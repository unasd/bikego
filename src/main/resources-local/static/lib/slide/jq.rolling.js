/********************************************
jQuery Plugin .. jq.rolling.js
url.. http://nunbi.co.kr/dev/jquery-rolling
copyright 2013 mirrh
********************************************/

(function($){
	"use strict";
	
	function Rolling(o,w,h,params){
		
		var frame = null;
		var wrapper = null;
		var settings = {
			items: 1,
			delay: 2500,
			duration: 500,
			rolling: 'page',
			autoscroll: 1,
			flow: 0
			};
		var item = {
			width: 0,
			length: 0
			};
		var page = {
			total: 1,
			now: 1,
			width: 0
			};
		var scroll = {
			pos: 0,
			width: 0,
			stop: 0
			}
		var rollingTimeIntervalID = 0;
		var self = this;

		this.scroll = scroll;

		init(o,w,h,params);
		function init(o,w,h,params){
			settings = $.extend(settings,params || {});
			frame = o;
			//frame.wrap("<div></div>");
			wrapper = frame.parent();
			//wrapper.append("<ul class='pages'></ul>");

			$("li",frame).css("width",w / settings.items);

			item.width = $("li",frame).outerWidth() + 1;
			item.length = $("li",frame).length;
			page.total = Math.ceil(item.length / settings.items);
			page.width = item.width * settings.items;

			frame.addClass("frame");
			frame.width(settings.items * item.width);
			frame.height(h);
			wrapper.width(settings.items * item.width);
			$("ul",frame).width((settings.items + item.length) * item.width);
			setPages();

			$("ul",frame).css("left",0);
			scroll.width = (settings.rolling=="item") ? item.width : page.width;

			if (settings.flow) cloneFlow();

			if (settings.autoscroll) rollingTimeIntervalID = setInterval(rolling, settings.delay);
		}

		function cloneFlow(){
			for (var i=0;i<settings.items;i++){
				$("ul",frame).append($("li",frame).eq(i).clone());
			}
		}

		function setPages(){
			for (var i=0;i<page.total;i++){
				$(".pages",wrapper).append("<li></li>");
			}
			$(".pages li",wrapper).eq(0).addClass("active");
			naviEvent();
		}

		function activePage(page){
			$(".pages li",wrapper).removeClass("active");
			$(".pages li",wrapper).eq(page-1).addClass("active");
		}

		function rolling(){
			if (scroll.stop) return;
			scroll.pos = parseInt($("ul",frame).css("left"));
			scroll.pos -= scroll.width;
			if (!settings.flow && scroll.pos<-page.width*(page.total-1)) scroll.pos = 0;

			var p = Math.floor(scroll.pos / -page.width) + 1;
			if (p==page.now){
				$("ul",frame).animate({"left": scroll.pos + "px"},{duration: settings.duration, easing: "easeOutExpo", complete:function(){
					if (settings.flow && parseInt($(this).css("left"))<-page.width*(page.total-1)){
						page.now = 1; activePage(1);
						$(this).css("left",0);
					}
				}});
			} else self.goPage(p);
		}

		function naviEvent(){
			var ev = (settings.autoscroll) ? "click" : "mouseover";
			$(".pages li",wrapper).bind(ev,function(){
				var idx = $(this).index();
				self.goPage(idx+1,1);
			});

			$("li",frame).mouseover(function(){
				self.scroll.stop = 1;
			});

			$("li",frame).mouseout(function(){
				self.scroll.stop = 0;
			});

		}

		this.next = function(){
			this.goPage(page.now+1,1);
		}
		this.prev = function(){
			if (page.now==1) page.now = page.total + 1;
			this.goPage(page.now-1,1);
		}

		this.goPage = function(p,c){
			if (settings.autoscroll && c){
				clearInterval(rollingTimeIntervalID);
				rollingTimeIntervalID = setInterval(rolling, settings.delay);
			}
			page.now = p;
			scroll.pos = -page.width * (p-1);
			activePage(p);
			$("ul",frame).animate({"left": scroll.pos + "px"},{duration: settings.duration, easing: "easeOutExpo", complete:function(){
				if (settings.flow && parseInt($(this).css("left"))<-page.width*(page.total-1)){
					page.now = 1; activePage(1);
					$(this).css("left",0);
				}
			}});
		}

	}

	$.fn.rolling = function(w,h,params){
		return new Rolling(this,w,h,params);
	};
})(jQuery);