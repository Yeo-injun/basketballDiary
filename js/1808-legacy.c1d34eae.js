"use strict";(self["webpackChunkbasketballdiary_frontend"]=self["webpackChunkbasketballdiary_frontend"]||[]).push([[1808,9968],{89968:function(){},62005:function(e,t,n){var r=n(33435),a=r.Z.createAxiosInstance("/teams");t["Z"]={searchTeamList:function(e){return a.get("",{params:e})},registerTeam:function(e){return a.post("",e)}}},51501:function(e,t,n){n.r(t),n.d(t,{default:function(){return v}});var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-container",[e._v(" 팀 등록 화면 "),n("TeamInfoFormComp",{on:{"e-team-info":e.setTeamInfo}}),n("v-btn",{attrs:{color:"primary",depressed:""},on:{click:function(t){return e.registerTeam()}}},[e._v(" 등록 ")])],1)},a=[],o=n(16198),s=(n(35666),n(62005)),u=n(83323),c={components:{TeamInfoFormComp:u["default"]},data:function(){return{teamInfo:{}}},watch:{},methods:{setTeamInfo:function(e){this.teamInfo=e},registerTeam:function(){var e=this;return(0,o.Z)(regeneratorRuntime.mark((function t(){var n,r,a;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(null!=e.teamInfo&&void 0!=e.teamInfo){t.next=3;break}return alert("팀 정보를 입력해주세요."),t.abrupt("return");case 3:return n=e.teamInfo,t.prev=4,t.next=7,s.Z.registerTeam(n);case 7:r=t.sent,a=r.data,console.log(a),alert("팀이 정상적으로 등록됐습니다."),e.$router.push({name:"MyTeamListPage"}),t.next=17;break;case 14:t.prev=14,t.t0=t["catch"](4),console.log(t.t0);case 17:case"end":return t.stop()}}),t,null,[[4,14]])})))()}},mounted:function(){}},i=c,f=n(1001),m=n(43453),l=n.n(m),p=n(680),d=n(99846),h=(0,f.Z)(i,r,a,!1,null,"4d833198",null),v=h.exports;l()(h,{VBtn:p.Z,VContainer:d.Z})}}]);
//# sourceMappingURL=1808-legacy.c1d34eae.js.map