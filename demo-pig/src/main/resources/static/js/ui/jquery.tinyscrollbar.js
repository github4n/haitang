/* user-reg/1.0.0 jquery.tinyscrollbar.js Date:2017-07-11 10:41:36 */ ! function(a) {
    "function" == typeof define && define.amd ? define("ui/jquery.tinyscrollbar.js", [], a)
        : "object" == typeof exports ? module.exports = a(require("jquery")) : a(jQuery)
}(function(a) {
    "use strict";
    var b = "tinyscrollbar",
        c = {
            axis: "y",
            wheel: !0,
            wheelSpeed: 40,
            wheelLock: !0,
            touchLock: !0,
            trackSize: !1,
            thumbSize: !1,
            thumbSizeMin: 20
        };

    function d(d, e) {
        this.options = a.extend({}, c, e), this._defaults = c, this._name = b;
        var f = this,
            g = d.find(".viewport"),
            h = d.find(".overview"),
            i = d.find(".scrollbar"),
            j = i.find(".track"),
            k = i.find(".thumb"),
            l = "ontouchstart" in document.documentElement,
            m = "onwheel" in document.createElement("div") ? "wheel" : void 0 !== document.onmousewheel ? "mousewheel" : "DOMMouseScroll",
            n = "x" === this.options.axis,
            o = n ? "width" : "height",
            p = n ? "left" : "top",
            q = 0;
        this.contentPosition = 0, this.viewportSize = 0, this.contentSize = 0, this.contentRatio = 0, this.trackSize = 0, this.trackRatio = 0, this.thumbSize = 0, this.thumbPosition = 0, this.hasContentToSroll = !1;

        function r() {
            return f.update(), t(), f
        }
        this.update = function(a) {
            var b = o.charAt(0).toUpperCase() + o.slice(1).toLowerCase();
            if (this.viewportSize = g[0]["offset" + b], this.contentSize = h[0]["scroll" + b], 0 != this.viewportSize || 0 != this.contentSize) {
                switch (this.contentRatio = this.viewportSize / this.contentSize, this.trackSize = this.options.trackSize || this.viewportSize, this.thumbSize = Math.min(this.trackSize, Math.max(this.options.thumbSizeMin, this.options.thumbSize || this.trackSize * this.contentRatio)), this.trackRatio = (this.contentSize - this.viewportSize) / (this.trackSize - this.thumbSize), this.hasContentToSroll = this.contentRatio < 1, i.toggleClass("disable", !this.hasContentToSroll), a) {
                    case "bottom":
                        this.contentPosition = Math.max(this.contentSize - this.viewportSize, 0);
                        break;
                    case "relative":
                        this.contentPosition = Math.min(Math.max(this.contentSize - this.viewportSize, 0), Math.max(0, this.contentPosition));
                        break;
                    default:
                        this.contentPosition = parseInt(a, 10) || 0
                }
                return this.thumbPosition = this.contentPosition / this.trackRatio, s(), f
            }
        };

        function s() {
            k.css(p, f.thumbPosition), h.css(p, -f.contentPosition), i.css(o, f.trackSize), j.css(o, f.trackSize), k.css(o, f.thumbSize)
        }

        function t() {
            l && (g[0].ontouchstart = function(a) {
                1 === a.touches.length && (a.stopPropagation(), w(a.touches[0]))
            }), k.bind("mousedown", function(a) {
                a.stopPropagation(), w(a)
            }), j.bind("mousedown", function(a) {
                w(a, !0)
            }), f.options.wheel && window.addEventListener ? d[0].addEventListener(m, x, !1) : f.options.wheel && (d[0].onmousewheel = x)
        }

        function u() {
            return f.contentPosition > 0
        }

        function v() {
            return f.contentPosition <= f.contentSize - f.viewportSize - 5
        }

        function w(c, d) {
            f.hasContentToSroll && (a("body").addClass("noSelect"), q = d ? k.offset()[p] : n ? c.pageX : c.pageY, l && (document.ontouchmove = function(a) {
                (f.options.touchLock || u() && v()) && a.preventDefault(), a.touches[0][b + "Touch"] = 1, y(a.touches[0])
            }, document.ontouchend = z), a(document).bind("mousemove", y), a(document).bind("mouseup", z), k.bind("mouseup", z), j.bind("mouseup", z), y(c))
        }

        function x(b) {
            if (f.hasContentToSroll) {
                var c = b || window.event,
                    e = -(c.deltaY || c.detail || -1 / 3 * c.wheelDelta) / 40,
                    g = 1 === c.deltaMode ? f.options.wheelSpeed : 1;
                f.contentPosition -= e * g * f.options.wheelSpeed, f.contentPosition = Math.min(f.contentSize - f.viewportSize, Math.max(0, f.contentPosition)), f.thumbPosition = f.contentPosition / f.trackRatio, d.trigger("move"), k.css(p, f.thumbPosition), h.css(p, -f.contentPosition), (f.options.wheelLock || u() && v()) && (c = a.event.fix(c), c.preventDefault())
            }
            b && b.stopPropagation()
        }

        function y(a) {
            if (f.hasContentToSroll) {
                var c = n ? a.pageX : a.pageY,
                    e = a[b + "Touch"] ? q - c : c - q,
                    g = Math.min(f.trackSize - f.thumbSize, Math.max(0, f.thumbPosition + e));
                f.contentPosition = g * f.trackRatio, d.trigger("move"), k.css(p, g), h.css(p, -f.contentPosition)
            }
        }

        function z() {
            f.thumbPosition = parseInt(k.css(p), 10) || 0, a("body").removeClass("noSelect"), a(document).unbind("mousemove", y), a(document).unbind("mouseup", z), k.unbind("mouseup", z), j.unbind("mouseup", z), document.ontouchmove = document.ontouchend = null
        }
        return r()
    }
    a.fn[b] = function(c) {
        return this.each(function() {
            a.data(this, "plugin_" + b) || a.data(this, "plugin_" + b, new d(a(this), c))
        })
    }
});