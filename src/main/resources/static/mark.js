/**
 * 作为为图片做标注的逻辑实现
 */
var Mark = function (){
	'use strict';
	this.isDraw = false;//开关
	this.type = "pencil";
	this.penal = document.getElementById("penal");
	this.pen = this.penal.getContext("2d");
	this.color = document.getElementById("color");
	this.lineWidth = document.getElementById("lineWidth");
	this.tools = document.getElementById("tools");
	this.img = new Image();
};
 
Mark.prototype.init = function(){
	'use strict';
	var self = this;
	var originX = null;
	var originY = null;
	
	this.tools.addEventListener('click', function(event){
		if(event.target.id === "pencil"){
			self.type = "pencil";
		}
		else if(event.target.id === "square"){
			self.type = "square";
		}
		else if(event.target.id === "circle"){
			self.type = "circle";
		}
	}, false);
	
	
	this.penal.addEventListener('mouseleave',function(){//鼠标离开
		if(self.isDraw){
			self.pen.closePath();
			self.isDraw = false; 
		}
	}, false);
	
	this.penal.addEventListener("mouseup", function(event){//松开鼠标
		if(self.isDraw){
			//self.pen.closePath();
			self.isDraw = false;
		}
	},false);
	
	this.penal.addEventListener("mousemove",function(event){//鼠标移动
		if(self.isDraw){
			var x = event.pageX - self.penal.offsetLeft;
			var y = event.pageY - self.penal.offsetTop ;
			
			if(self.type === "pencil"){//铅笔
				self.pen.lineTo(x,y);
				self.pen.stroke();
			}
			else if(self.type === "square"){//方形
				
			}
			else if(self.type === "circle"){//圆形
				
			}
			
		}
	}, false);
	
	this.penal.addEventListener("mousedown", function(event){//按下鼠标
		self.isDraw = true;
		self.pen.beginPath();
	},false);
};
