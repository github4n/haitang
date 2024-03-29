/* jdf-1.0.0/ dialog.js Date:2017-07-17 14:18:03 */ ! function(a) {
    a.ui.define("dialog", {
        options: {
            hasCssLink: !0,
            baseVersion: "1.0.0",
            cssLinkVersion: "1.0.0",
            maskHas: !0,
            maskClass: "ui-mask",
            maskIframe: !1,
            maskClose: !1,
            opacity: .15,
            zIndex: 9998,
            type: "text",
            source: null,
            extendMainClass: null,
            autoIframe: !0,
            autoOpen: !0,
            autoCloseTime: 0,
            title: !0,
            hasButton: !1,
            submitButton: "\u786e\u8ba4",
            cancelButton: "\u53d6\u6d88",
            onSubmit: null,
            onCancel: null,
            onBeforeClose: null,
            closeButton: !0,
            onReady: null,
            width: 480,
            height: null,
            fixed: !1,
            autoUpdate: !1,
            maskId: null,
            mainId: null,
            contentId: null,
            titleId: null,
            iframeName: "dialogIframe",
            iframeTimestamp: !0
        },
        init: function() {
            var b = this.options;
            a.browser.isIE6() && (b.fixed = !1), this.createMain(), this.createMask(), this.mainStyle(), b.autoOpen ? this.open() : this.hide(), this.bind()
        },
        show: function() {
            this.mask && this.mask.show(), this.el.show()
        },
        hide: function() {
            this.mask && this.mask.hide(), this.el.hide()
        },
        tpl: {
            mask: '<div class="ui-mask"></div>',
            close: '<a class="ui-dialog-close" title="\u5173\u95ed"><span class="ui-icon ui-icon-delete"></span></a>',
            title: '<div class="ui-dialog-title">						<span><%=title%></span>					</div>				',
            wrap: '<div class="ui-dialog"></div>',
            conten: '<div class="ui-dialog-content"></div>',
            button: '<div class="ui-dialog-btn">						<%if($.trim(submit)){%><a class="ui-dialog-btn-submit"><%=submit%></a><%}%>						<%if($.trim(cancel)){%><a class="ui-dialog-btn-cancel"><%=cancel%></a><%}%>					</div>				'
        },
        createMain: function() {
            var c = this.options;
            var d = "";
            c.title && (d = a.tpl(this.tpl.title, {
                title: c.title
            }));
            var e = a.tpl(this.tpl.button, {
                submit: this.options.submitButton,
                cancel: this.options.cancelButton
            });
            var f = d + this.tpl.conten + (c.hasButton ? e : "");
            this.el = a(this.tpl.wrap), c.extendMainClass && this.el.addClass(c.extendMainClass), a(f).appendTo(this.el), this.el.appendTo("body"), this.content = this.el.find(".ui-dialog-content"), this.title = this.el.find(".ui-dialog-title"), c.mainId && this.el.attr("id", c.mainId), c.contentId && this.content.attr("id", c.contentId), c.titleId && this.title.attr("id", c.titleId), c.closeButton && this.el.append(this.tpl.close)
        },
        createMask: function() {
            var b = this;
            var c = this.options;
            if (c.maskHas) {
                {
                    this.mask = a(document.createElement("div"))
                }
                this.mask.addClass(c.maskClass).css({
                    position: "absolute",
                    left: 0,
                    top: 0,
                    opacity: c.opacity,
                    zIndex: c.zIndex,
                    backgroundColor: "#000",
                    width: a.page.docWidth(),
                    height: a.page.docHeight()
                }), c.maskId && this.mask.attr("id", c.maskId), a("." + c.maskClass)[0] || this.mask.appendTo("body"), (a.browser.isIE6() || c.maskIframe) && this.mask.append('<iframe src="about:blank" class="jdMaskIframe" frameBorder="0" style="width:100%;height:100%;position:absolute;z-index:' + (c.zIndex + 1) + ';opacity:0;filter:alpha(opacity=0);top:0;left:0;">'), a(window).resize(function() {
                    b.mask.css({
                        width: a.page.docWidth(),
                        height: a.page.docHeight()
                    })
                })
            }
        },
        getPadding: function(a) {
            return {
                width: parseInt(a.css("paddingLeft"), 10) + parseInt(a.css("paddingRight"), 10),
                height: parseInt(a.css("paddingTop"), 10) + parseInt(a.css("paddingBottom"), 10)
            }
        },
        mainStyle: function() {
            var b = this.options;
            b.title && (b.height = b.height ? b.height + 28 : b.height, this.title.css({
                width: b.width - this.getPadding(this.content).width
            })), this.content.css({
                height: b.height ? b.height : "",
                width: b.width ? b.width - this.getPadding(this.content).width : "",
                overflow: "hidden"
            }), b.width && this.el.css({
                width: b.width
            });
            var c = b.fixed && !a.browser.isIE6() ? "fixed" : "absolute";
            this.el.css({
                position: c,
                zIndex: b.zIndex + 2,
                display: "block",
                overflow: "hidden"
            }), this.updateMain()
        },
        updateMain: function() {
            var b = this.options;
            var c = a.page.docWidth() != a.page.clientWidth() ? 16 : 0;
            var d = b.fixed ? 0 : a(document).scrollTop();
            var e = b.fixed ? 0 : a(document).scrollLeft();
            var f = (a.page.clientHeight() - this.el.outerHeight()) / 2 + d;
            var g = a.browser.msie && a.browser.version < 10 ? 0 : 8;
            var h = (a.page.clientWidth() - c - (b.width ? b.width + g : 0)) / 2 + e;
            0 > f && (f = 0), 0 > h && (h = 0), this.el.css({
                top: f,
                left: h
            })
        },
        bind: function() {
            var b = this;
            var c = this.options;
            this.options.closeButton && this.el.find(".ui-dialog-close").bind("click", function() {
                b.close()
            }), this.options.autoUpdate && a(window).resize(function() {
                b.updateMain()
            }), c.hasButton && (this.el.find(".ui-dialog-btn-submit").bind("click", function() {
                c.onSubmit && c.onSubmit.call(this)
            }), this.el.find(".ui-dialog-btn-cancel").bind("click", function() {
                b.close()
            })), this.options.maskHas && this.options.maskClose && a(this.mask).bind("click", function() {
                b.close()
            })
        },
        open: function() {
            this.openType(), this.autoClose(), this.show(), this.iframeSet(), this.options.onReady && this.options.onReady.call(this)
        },
        openType: function() {
            var b = this.options;
            var c = this;
            switch (b.type) {
                case "text":
                    this.content.html(b.source);
                    break;
                case "html":
                    a(b.source).clone().appendTo(this.content);
                    break;
                case "iframe":
                    var d = {
                        width: "100%",
                        height: "100%"
                    };
                    b.iframeTimestamp && !/&t=/.test(b.source) && (b.source += (b.source.indexOf("?") > -1 ? "&" : "?") + "t=" + (new Date).getTime()), this.iframe = a('<iframe src="' + b.source + '" id="' + b.iframeName + '" name="' + b.iframeName + '" marginwidth="0" marginheight="0" frameborder="0" scrolling="no" style="border:0"></iframe>').css(d).appendTo(this.content);
                    break;
                case "image":
                    var e = b.width ? 'width="' + b.width + '"' : "";
                    var f = b.height ? 'height="' + b.height + '"' : "";
                    var g = a("<img src=" + b.source + " " + e + f + "/>");
                    g.appendTo(this.content), g.bind("load", function() {
                        c.updateMain()
                    });
                    break;
                case "json":
            }
            c.updateMain()
        },
        close: function() {
            var a = this.options;
            this.options.autoCloseTime;
            a.onBeforeClose && a.onBeforeClose.call(this), this.el.remove(), this.mask && this.mask.remove(), a.onCancel && a.onCancel.call(this)
        },
        autoClose: function() {
            var b = this;
            var c = this.options.autoCloseTime;
            if (c) {
                var d = c;
                a("<div class='ui-dialog-autoclose'><span id='ui-autoclose'>" + d + "</span>\u79d2\u540e\u81ea\u52a8\u5173\u95ed</div>").appendTo(this.el), clearInterval(window.autoCloseTimerDialog), window.autoCloseTimerDialog = setInterval(function() {
                    d--, a("#ui-autoclose").html(d), 0 == d && (d = c, b.close(), clearInterval(window.autoCloseTimerDialog))
                }, 1e3), this.updateMain()
            }
        },
        getIframeHeight: function(a) {
            var b = a[0].contentWindow.document;
            return b.body.scrollHeight && b.documentElement.scrollHeight ? Math.min(b.body.scrollHeight, b.documentElement.scrollHeight) : b.documentElement.scrollHeight ? b.documentElement.scrollHeight : b.body.scrollHeight ? b.body.scrollHeight : void 0
        },
        syncHeight: function() {
            var a = this;
            var b;
            try {
                b = a.getIframeHeight(a.iframe)
            } catch (c) {}
            b && (a.iframe.css({
                height: b
            }), a.updateMain())
        },
        iframeSet: function() {
            var a = this;
            var b = this.options;
            "iframe" == b.type && b.autoIframe && this.iframe.one("load", function() {
                a.syncHeight()
            })
        }
    }), a.closeDialog = function() {
        a(".ui-dialog,.ui-mask").remove(), clearInterval(window.autoCloseTimerDialog)
    }
}(jQuery);