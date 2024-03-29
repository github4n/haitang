/* user-passport/1.0.0 login.username.js Date:2016-10-27 18:10:22 */
define("login/login.username.js",

    [
        "util/util.reg.js",
        "login/login.status.js",
        "login/login.iframe.js",
        "login/login.pubSub.js"
    ],

    function(require) {
    var c = require("util/util.reg.js");
    var d = require("login/login.status.js");
    var e = require("login/login.iframe.js");
    var f = require("login/login.pubSub.js");
    var g = function(a) {
        var b = ["qq.com", "163.com", "126.com", "sina.com", "hotmail.com", "gmail.com", "sohu.com", "139.com", "189.cn"];
        var c = ["<ul>"];
        var d = $("input[name=loginname]").parent();
        if (!/^.+@/.test(a)) return j.modal.remove(), d.find(".sugcontent").remove();
        if (a.length < 1) return j.modal.remove(), d.find(".sugcontent").remove();
        var e = a.match(/.+@([^@]+$)/) || [];
        if (e.length > 1) {
            /@+([^@]+$)/.test(a) && (e = a.match(/@+([^@]+$)/));
            var f = new RegExp("^" + e[1].replace(/\\/g, "\\\\"));
            b = $.grep(b, function(a) {
                return f.test(a)
            })
        }
        for (var g = 0, h = b.length; h > g; g++) {
            var i = "<li " + (0 == g ? 'class="hover"' : "") + ">" + a.match(/^.+@/) + b[g] + "</li>";
            c.push(i)
        }
        c.push("</ul>"), d.find(".sugcontent").remove(), h > 0 ? (d.append('<div class="sugcontent">' + c.join("") + "</div>"), j.modal.create()) : j.modal.remove()
    };
    var h = {
        "username.focus": function() {
            f.subscribe("username.focus", function() {
                d.onEvent({
                    ele: j.ele.parent(),
                    addClass: "item-focus",
                    callback: function() {
                        var a = j.ele.val();
                        c.isEmpty(a) || j.ele.siblings(".clear-btn").show(), $("#entry").removeClass("item-focus")
                    }
                })
            })
        },
        "username.blur": function() {
            f.subscribe("username.blur", function() {
                d.onEvent({
                    ele: j.ele.parent(),
                    removeClass: "item-focus",
                    callback: function() {
                        var a = j.ele.val();
                        $("#o-authcode").is(":visible");
                        c.isEmpty(a) || f.publish("login.autologin"), $(".sugcontent li.hover").click()
                    }
                })
            })
        },
        "username.keyup": function() {
            f.subscribe("username.keyup", function(a) {
                38 == a.keyCode || 40 == a.keyCode ? $(".sugcontent li").size() && f.publish("suggest.selectByKey", a) : d.onEvent({
                    callback: function() {
                        var a = j.ele.val();
                        c.isEmpty(a) ? j.ele.siblings(".clear-btn").hide() : j.ele.siblings(".clear-btn").show(), g(a)
                    }
                })
            })
        },
        "login.autologin": function() {
            f.subscribe("login.autologin", function() {
                var a = readCookie("alpin");
                var b = encodeURIComponent($("#loginname").val());
                var c = $("#o-authcode").is(":visible");
                var d = false;
                b && !c && (d = true, showAuthCode()),
                null != a && "" != a &&
                (null != a && "" != a && a == b ?
                    ($("#autoLogin").prop("checked", true),
                    f.publish("username.autologinInfo"),
                    !d && !c && showAuthCode()) :
                    ($("#autoLogin").prop("checked", false), $(".msg-warn", ".msg-wrap").hide()))
            })
        },
        "suggest.selectByKey": function() {
            f.subscribe("suggest.selectByKey", function(a) {
                var b = $(".sugcontent ul");
                var c = b.find("li.hover").index();
                var d = b.find("li").size() - 1;
                var e;
                38 == a.keyCode && -1 == --c && (c = d), 40 == a.keyCode && ++c > d && (c = 0), e = b.find("li:eq(" + c + ")").addClass("hover"), e.siblings().removeClass("hover"), j.ele.val(e.text())
            })
        },
        "form.init": function() {
            f.subscribe("form.init", function() {
                f.publish("username.autologinInfo");
                var a = j.ele.val();
                c.isEmpty(a) || j.ele.siblings(".clear-btn").show()
            })
        },
        "username.autologinInfo": function() {
            f.subscribe("username.autologinInfo", function() {
                var a = $("#autoLogin").prop("checked");
                var b = '<div class="msg-warn"><b></b>\u516c\u5171\u573a\u6240\u4e0d\u5efa\u8bae\u81ea\u52a8\u767b\u5f55\uff0c\u4ee5\u9632\u8d26\u53f7\u4e22\u5931</div>';
                a && ($(".msg-wrap .msg-error").hide(), $(".msg-wrap .msg-warn").replaceWith(b))
            })
        },
        "username.clear": function() {
            f.subscribe("username.clear", function() {
                d.onClear(function() {
                    g(""), f.publish("iframe.remove")
                })
            })
        },
        "suggest.click": function() {
            f.subscribe("suggest.click", function() {
                $(".form").delegate(".sugcontent li", "click", function() {
                    var a = $(this).text();
                    var b = document.getElementById("_ocx_password");
                    var c = document.getElementById("loginname");
                    j.ele.val(a), f.publish("iframe.remove"), f.publish("passwordField.focus"), c && c.blur(), setTimeout(function() {
                        b && b.focus()
                    }, 300), g("")
                })
            })
        },
        "suggest.hover": function() {
            f.subscribe("suggest.click", function() {
                $(".form").delegate(".sugcontent li", "mouseover", function() {
                    var a = $(this).addClass("hover");
                    a.siblings().removeClass("hover")
                })
            })
        },
        "iframe.create": function() {
            f.subscribe("iframe.create", function() {
                j.modal = new e({
                    coverEle: ".sugcontent",
                    callback: function(a) {
                        $(".sugcontent").after(a)
                    }
                })
            })
        },
        "iframe.remove": function() {
            f.subscribe("iframe.remove", function() {
                j.modal && j.modal.remove()
            })
        },
        "suggest.enterClick": function() {
            f.subscribe("suggest.enterClick", function() {
                $(".sugcontent li.hover").click()
            })
        }
    };
    //绑定事件
    for (var i in h) h.hasOwnProperty(i) && h[i]();
    //用户名框对像
    var j = {
        ele: $("#loginname"),
        checkBoxEle: $("#autoLogin, #chkOpenCtrl"),
        init: function() {
            this.ele.bind("focus", function() {
                f.publish("username.focus")
            }),
            this.ele.bind("blur", function() {
                clearTimeout(j.timerBlurid), j.timerBlurid = setTimeout(function() {
                    f.publish("username.blur")
                }, 1)
            }),
            this.ele.bind("keyup", function(a) {
                return 13 == a.keyCode ? f.publish("suggest.enterClick") : void f.publish("username.keyup", a)
            }),
            this.checkBoxEle.bind("keyup", function(a) {
                13 == a.keyCode && ($("#loginsubmit").click(), $(this).blur())
            }),
            f.publish("login.autologin"),
            f.publish("form.init"),
            f.publish("suggest.click"),
            f.publish("suggest.hover"),
            f.publish("username.clear"),
            f.publish("iframe.create")
        }
    };
    return j
});