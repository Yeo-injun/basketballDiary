"use strict";(self["webpackChunkbasketballdiary_frontend"]=self["webpackChunkbasketballdiary_frontend"]||[]).push([[4110],{17273:function(){},99846:function(t,n,r){r.d(n,{Z:function(){return o}});r(57327),r(41539),r(47941),r(23157),r(92222),r(68582),r(17273),r(73210),r(69600);var e=r(20144);function a(t){return e.Z.extend({name:"v-".concat(t),functional:!0,props:{id:String,tag:{type:String,default:"div"}},render:function(n,r){var e=r.props,a=r.data,i=r.children;a.staticClass="".concat(t," ").concat(a.staticClass||"").trim();var o=a.attrs;if(o){a.attrs={};var s=Object.keys(o).filter((function(t){if("slot"===t)return!1;var n=o[t];return t.startsWith("data-")?(a.attrs[t]=n,!1):n||"string"===typeof n}));s.length&&(a.staticClass+=" ".concat(s.join(" ")))}return e.id&&(a.domProps=a.domProps||{},a.domProps.id=e.id),n(e.tag,a,i)}})}var i=r(76290),o=a("container").extend({name:"v-container",functional:!0,props:{id:String,tag:{type:String,default:"div"},fluid:{type:Boolean,default:!1}},render:function(t,n){var r,e=n.props,a=n.data,o=n.children,s=a.attrs;return s&&(a.attrs={},r=Object.keys(s).filter((function(t){if("slot"===t)return!1;var n=s[t];return t.startsWith("data-")?(a.attrs[t]=n,!1):n||"string"===typeof n}))),e.id&&(a.domProps=a.domProps||{},a.domProps.id=e.id),t(e.tag,(0,i.ZP)(a,{staticClass:"container",class:Array({"container--fluid":e.fluid}).concat(r||[])}),o)}})},44110:function(t,n,r){r.r(n),r.d(n,{default:function(){return p}});var e=function(){var t=this,n=t.$createElement,r=t._self._c||n;return r("v-container",[r("v-btn",{attrs:{color:"error"},on:{click:t.doDelete}},[t._v(t._s(this.pBtnName))])],1)},a=[],i={props:{pBtnName:String},data:function(){return{eventEmitName:"do-delete"}},methods:{doDelete:function(){this.$emit(this.eventEmitName)}}},o=i,s=r(1001),c=r(43453),d=r.n(c),l=r(680),u=r(99846),f=(0,s.Z)(o,e,a,!1,null,"1afc1bfe",null),p=f.exports;d()(f,{VBtn:l.Z,VContainer:u.Z})}}]);
//# sourceMappingURL=4110-legacy.d0724ff7.js.map