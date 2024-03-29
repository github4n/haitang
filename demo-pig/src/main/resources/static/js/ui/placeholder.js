/* jdf-1.0.0/ placeholder.js Date:2017-07-17 14:18:03 */
! function(a) {
    a.ui.define("placeholder",
        {
        options: {
            color: "#ccc",
            isValue: !1,
            leftDiff: 0,
            topDiff: 0
        },
        init: function() {
            this.options.isValue ? this.asValue() : this.asPlaceholder();
            var b = this;
            a(document).click(function(c) {
                var d = c.target;
                b.isTxt(d) && a(d).next("input[placeholder], textarea").focus()
            })
        },
        asValue: function() {
            var b = this;
            b.appendTxt(), b.isShow(), a(this.el).focus(function() {
                a(this).prev("txt").hide(), a(this).val() == a(this).attr("data-placeholder") && a(this).val("")
            }).blur(function() {
                b.isShow()
            }), a(this.el).click(function() {
                a(this).focus()
            })
        },
        asPlaceholder: function() {
            var b = this;
            b.appendTxt(), b.isShow(), a(b.el).bind("keyup keydown change click", function() {
                "" == a(this).val() ? b.showTxt() : b.hideTxt()
            })
        },
        appendTxt: function() {
            var b = this;
            var c = b.el;
            var d = document.createElement("txt");
            var e = b.options.color;
            c.prev().is("txt") ? d = c.prev() : c.before(d), a(d).text(c.attr("placeholder")), c.attr("placeholder", " "), c.is("input") && a(d).css({
                position: "absolute",
                "z-index": "2",
                "line-height": c.outerHeight() + "px",
                "margin-left": parseInt(c.css("padding-left")) + parseInt(c.css("border-left-width")) + parseInt(b.options.leftDiff),
                "margin-top": b.options.topDiff,
                "font-size": c.css("font-size"),
                "font-family": c.css("font-family"),
                color: e
            }), c.is("textarea") && a(d).css({
                position: "absolute",
                "z-index": "2",
                "line-height": 1,
                "margin-left": parseInt(c.css("padding-left")) + parseInt(c.css("border-left-width")) + parseInt(b.options.leftDiff),
                "margin-top": parseInt(c.css("padding-top")) + parseInt(c.css("border-top-width")) + parseInt(b.options.topDiff),
                "font-size": c.css("font-size"),
                "font-family": c.css("font-family"),
                color: e
            }), "" != c.val() && b.hideTxt(this)
        },
        isShow: function() {
            "" == this.el.val() ? this.showTxt() : this.hideTxt()
        },
        isTxt: function(b) {
            return a(b).is("txt")
        },
        showTxt: function() {
            this.el.prev("txt").css("display", "inline")
        },
        hideTxt: function() {
            this.el.prev("txt").hide()
        }
    })
}(jQuery);