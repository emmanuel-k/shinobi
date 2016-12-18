function _createNodeStr(nodeName, nodeValue) {
    return '<' + nodeName + '>' + nodeValue + '</' + nodeName + '>';
}

// internal use only
function _recursiveObject2Xml(name, obj) {
    var xmlstr = '';
    if (typeof (obj) == 'string' || typeof (obj) == 'number') {
        xmlstr = _createNodeStr(name, obj);
    } else if (jQuery.isArray(obj)) {
        jQuery.each(obj, function(idx, item) {
            xmlstr += _recursiveObject2Xml(name, item);
        });
    } else if (typeof (obj) == 'object') {
        xmlstr += '<' + name + '>';
        jQuery.each(obj, function(propName, propVal) {
            xmlstr += _recursiveObject2Xml(propName, propVal);
        });
        xmlstr += '</' + name + '>';
    }
    return xmlstr;
}

function object2xml(name, obj) {
    var xmlstr = '<?xml version="1.0" encoding="UTF-8"?>';
    xmlstr += _recursiveObject2Xml(name, obj);
    return xmlstr;
}

// internal use only
function _recursiveXml2Object($xml) {
    if ($xml.children().size() > 0) {
        var _obj = {};
        $xml.children().each( function() {
            var _childObj = ($(this).children().size() > 0) ? _recursiveXml2Object($(this)) : $(this).text();
            if ($(this).siblings().size() > 0 && $(this).siblings().get(0).tagName == this.tagName) {
                if (_obj[this.tagName] == null) {
                    _obj[this.tagName] = [];
                }
                _obj[this.tagName].push(_childObj);
            } else {
                _obj[this.tagName] = _childObj;
            }
        });
        return _obj;
    } else {
        return $xml.text();
    }
}

// convert XML string to an Object.
// $xml, which is an jQuery xml object.
function xml2object($xml) {
    var obj = {};
    if ($xml.find('response').size() > 0) {
        var _response = _recursiveXml2Object($xml.find('response'));
        obj.type = 'response';
        obj.response = _response;
    } else if ($xml.find('error').size() > 0) {
        var _code = $xml.find('code').text();
        var _message = $xml.find('message').text();
        log.warn('MAIN : error code = ' + _code);
        log.warn('MAIN : error msg = ' + _message);
        obj.type = 'error';
        obj.error = {
            code: _code,
            message: _message
        };
    } else if ($xml.find('config').size() > 0) {
        var _config = _recursiveXml2Object($xml.find('config'));
        obj.type = 'config';
        obj.config = _config;
    } else {
        obj.type = 'unknown';
    }
    return obj;
}


function index_sendConnectionAction() {

    var dialup_xml = object2xml('request', g_dialup_action);
    g_freezeConnectionUpdate = true;
    saveAjaxData('api/dialup/dial', dialup_xml, function($xml) {
        var dialup_ret = xml2object($xml);
        if (isAjaxReturnOK(dialup_ret)) {
            log.debug('api/dialup/dial ok');
        } else {
            log.debug('api/dialup/dial error');
        }
    });
}