$(function () {
    function x_position() {
        return window.pageXOffset ?
            window.pageXOffset :
            document.documentElement.scrollLeft ?
                document.documentElement.scrollLeft :
                document.body.scrollLeft;
    }

    function y_position() {
        return window.pageYOffset ?
            window.pageYOffset :
            document.documentElement.scrollTop ?
                document.documentElement.scrollTop :
                document.body.scrollTop;
    }

    toggle_visibility();

    function toggle_visibility() {
        $("#main .section .section-queue .section-hidden h2").click(function () {
            const section = $("#main .section .section-queue .section-hidden")
            visible(section)

        })
        $("#main .section .section-bind .section-hidden h2").click(function () {
            const section = $("#main .section .section-bind .section-hidden")
            visible(section)
        })

        $("#main .section .section-hidden h2").click(function () {
            const section = $("#main .section .section-hidden")
            visible(section)
        })
    }

    function visible(section) {
        section.next().slideToggle(100);
        if (section.hasClass('section-invisible')) {
            section.removeClass('section-invisible');
            section.addClass('section-visible');
            section.find('.hider').attr('style', 'display:block;');
        } else {
            section.addClass('section-invisible');
            section.removeClass('section-visible');
            section.find('.hider').attr('style', 'display:none;');
        }

    }


    //获取url中的参数方法
    function getUrlParam(name) {
        //构造一个含有目标参数的正则表达式对象
        const reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        //匹配目标参数
        const r = window.location.search.substr(1).match(reg);
        //返回参数
        if (r != null) {
            return unescape(r[2]);
        } else {
            return null;
        }
    }


    function get(url, accept, callback) {
        const req = new XMLHttpRequest();
        req.open("GET", url);
        req.setRequestHeader("Accept", accept);
        req.send();

        req.onreadystatechange = function () {
            if (req.readyState === XMLHttpRequest.DONE) {
                callback(req);
            }
        };
    }

    function check_password(params) {
        if (params['password'] !== undefined) {
            if (params['password'] === '') {
                throw("Please specify a password.");
            }
            if (params['password'] !== params['password_confirm']) {
                throw("Passwords do not match.");
            }
            delete params['password_confirm'];
        }

        return params;
    }

    function debug(str) {
        $('<p>' + str + '</p>').appendTo('#debug');
    }

    function keys(obj) {
        const ks = [];
        for (const k in obj) {
            ks.push(k);
        }
        return ks;
    }

// Don't use the jQuery AJAX support, it seems to have trouble reporting
// server-down type errors.
    function xmlHttpRequest() {
        let res;
        try {
            res = new XMLHttpRequest();
        } catch (e) {
            res = new ActiveXObject("Microsoft.XMLHttp");
        }
        return res;
    }

// Our base64 library takes a string that is really a byte sequence,
// and will throw if given a string with chars > 255 (and hence not
// DTRT for chars > 127). So encode a unicode string as a UTF-8
// sequence of "bytes".

// encodeURIComponent handles utf-8, unescape does not. Neat!
    function encode_utf8(str) {
        return unescape(encodeURIComponent(str));
    }
})