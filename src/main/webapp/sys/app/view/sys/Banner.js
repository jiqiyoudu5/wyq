Ext.define('EJ.view.sys.Banner', {
			alias : 'widget.sysBanner',
			extend : 'Ext.panel.Panel',
			initComponent : function() {
				this.items = [{
							// autoLoad : './WEB-INF/pages/sys/head.html',
							height : 100,// 设置面板的高度
							border : false,
							bodyStyle : 'background-color:#157fcc'// 设置面板体的背景色
						}];
				this.bbar = ['<img src="./resources/img/user.png"/>', '-',
						CURRENT_USER.username + ' 您好！', '-',
						'今天是' + Ext.Date.format(new Date(), "Y年m月d日"), '->', {
							text : "注销登录",
							icon : './resources/img/logout.png',
							handler : function() {
								Ext.Msg.confirm('询问', '您是否确定要注销登录？',
										function(v) {
											if (v === 'yes') {
												window.location = './wyq_logout';
											}
										});
							}
						}];
				this.callParent(arguments);
			}
		});