"use strict";(self["webpackChunkbasketballdiary_frontend"]=self["webpackChunkbasketballdiary_frontend"]||[]).push([[6564],{17273:function(){},99846:function(e,t,a){a.d(t,{Z:function(){return c}});a(57327),a(41539),a(47941),a(23157),a(92222),a(68582),a(17273),a(73210),a(69600);var n=a(20144);function r(e){return n.Z.extend({name:"v-".concat(e),functional:!0,props:{id:String,tag:{type:String,default:"div"}},render:function(t,a){var n=a.props,r=a.data,o=a.children;r.staticClass="".concat(e," ").concat(r.staticClass||"").trim();var c=r.attrs;if(c){r.attrs={};var s=Object.keys(c).filter((function(e){if("slot"===e)return!1;var t=c[e];return e.startsWith("data-")?(r.attrs[e]=t,!1):t||"string"===typeof t}));s.length&&(r.staticClass+=" ".concat(s.join(" ")))}return n.id&&(r.domProps=r.domProps||{},r.domProps.id=n.id),t(n.tag,r,o)}})}var o=a(76290),c=r("container").extend({name:"v-container",functional:!0,props:{id:String,tag:{type:String,default:"div"},fluid:{type:Boolean,default:!1}},render:function(e,t){var a,n=t.props,r=t.data,c=t.children,s=r.attrs;return s&&(r.attrs={},a=Object.keys(s).filter((function(e){if("slot"===e)return!1;var t=s[e];return e.startsWith("data-")?(r.attrs[e]=t,!1):t||"string"===typeof t}))),n.id&&(r.domProps=r.domProps||{},r.domProps.id=n.id),e(n.tag,(0,o.ZP)(r,{staticClass:"container",class:Array({"container--fluid":n.fluid}).concat(a||[])}),c)}})},42189:function(e,t,a){a(92222);var n=a(33435),r=n.Z.createAxiosInstance("/myTeams");t["Z"]={searchTeams:function(e){return r.get("",{params:e})},modifyMyTeamsProfile:function(e,t){return r.post("/".concat(e,"/profile"),t,{headers:{"Content-Type":"multipart/form-data"}})},findMyTeamsProfile:function(e){return console.log("findMyTeamsProfile call : /".concat(e,"/profile")),r.get("/".concat(e,"/profile"))},searchManagers:function(e){return console.log("searchManagers call : /".concat(e,"/managers")),r.get("/".concat(e,"/managers"))},searchMembers:function(e,t){return console.log("searchMembers call : /".concat(e,"/members/pageNo=").concat(t)),r.get("/".concat(e,"/members"),{params:{pageNo:t}})},searchTeam:function(e){return r.get("/".concat(e,"/info"))},modifyMyTeam:function(e,t){return r.post("/".concat(e,"/info"),t)},searchInvitedPlayer:function(e){return r.get("/".concat(e.teamSeq,"/joinRequestsTo"),{params:{state:e.state}})},inviteTeamMember:function(e){return r.post("/".concat(e.teamSeq,"/joinRequestTo/").concat(e.userSeq))},searchJoinRequestPlayer:function(e){return r.get("/".concat(e.teamSeq,"/joinRequestsFrom"),{params:{state:e.state}})},approveJoinRequest:function(e){return r.patch("/".concat(e.teamSeq,"/joinRequestFrom/").concat(e.teamJoinRequestSeq,"/approval"))},rejectJoinRequest:function(e){return r.patch("/".concat(e.teamSeq,"/joinRequestFrom/").concat(e.teamJoinRequestSeq,"/rejection"))},appointManager:function(e){return r.patch("/".concat(e.teamSeq,"/members/").concat(e.teamMemberSeq,"/manager"))},dismissManager:function(e){return r.delete("/".concat(e.teamSeq,"/members/").concat(e.teamMemberSeq,"/manager"))},dischargeTeamMember:function(e){return r.delete("/".concat(e.teamSeq,"/members/").concat(e.teamMemberSeq))},searchMyTeamGames:function(e){return r.get("/".concat(e.teamSeq,"/games"))},searchAllTeamMembers:function(e){return r.get("/".concat(e.teamSeq,"/allTeamMembers"),{params:{pageNo:e.pageNo,playerName:e.playerName}})}}},26564:function(e,t,a){a.r(t),a.d(t,{default:function(){return g}});var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("v-container",[a("v-btn",{attrs:{color:"black white--text",small:""},on:{click:function(t){return e.dismissManager()}}},[e._v(" 임원제명 ")])],1)},r=[],o=a(16198),c=(a(35666),a(9653),a(42189)),s={props:{pTeamSeq:Number,pTeamMemberSeq:Number},methods:{dismissManager:function(){var e=this;return(0,o.Z)(regeneratorRuntime.mark((function t(){var a,n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return a={teamSeq:e.pTeamSeq,teamMemberSeq:e.pTeamMemberSeq},t.prev=1,t.next=4,c.Z.dismissManager(a);case 4:n=t.sent,"200"==n.status&&alert("관리자에서 해임되었습니다."),t.next=11;break;case 8:t.prev=8,t.t0=t["catch"](1),console.log(t.t0);case 11:case"end":return t.stop()}}),t,null,[[1,8]])})))()}}},i=s,u=a(1001),m=a(43453),l=a.n(m),f=a(680),p=a(99846),d=(0,u.Z)(i,n,r,!1,null,"e2ca1edc",null),g=d.exports;l()(d,{VBtn:f.Z,VContainer:p.Z})}}]);
//# sourceMappingURL=6564-legacy.27cc469b.js.map