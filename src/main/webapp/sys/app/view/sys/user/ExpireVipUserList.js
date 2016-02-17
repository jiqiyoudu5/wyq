Ext.define('EJ.view.sys.user.ExpireVipUserList', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.expireVipUserList',// 首页VIP到期提示
	title : '即将到期的VIP用户',
	id : 'ExpireVipUserList',
	store : 'sys.user.ExpireVipUserStore',
	columnLines : true,
	initComponent : function() {
		this.columns = [{
					xtype : 'rownumberer'
				}, {
					header : '用户名',
					dataIndex : 'username',
					flex : 1
				}, {
					header : '公司名称',
					dataIndex : 'businessName',
					flex : 1
				}, {
					header : '所在地区',
					dataIndex : 'district',
					flex : 1
				}, {
					header : '注册时间',
					dataIndex : 'regTime',
					renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
					flex : 1
				}, {
					header : 'VIP',
					dataIndex : 'vip',
					flex : 1,
					renderer : function(v) {
						switch (v) {
							case false :
								return '否';
								break;
							case true :
								return '<img src="./resources/img/vip.png"></img>';
								break;
						}
					}
				}, {
					header : 'VIP截止日期',
					dataIndex : 'vipendtime',
					renderer : Ext.util.Format.dateRenderer('Y-m-d'),
					flex : 1
				}, {
					header : '锁状态',
					dataIndex : 'locked',
					flex : 1,
					renderer : function(v) {
						switch (v) {
							case false :
								return '<img src="./resources/img/key.png"></img>';
								break;
							case true :
								return '<img src="./resources/img/lock.png"></img>';
								break;
						}
					}
				}, {
					header : '启用状态',
					dataIndex : 'enabled',
					flex : 1,
					renderer : function(v) {
						switch (v) {
							case false :
								return '未启用';
								break;
							case true :
								return '<font color=green>启用</font>';
								break;
						}
					}
				}];
		this.bbar = Ext.create('Ext.PagingToolbar', {
					store : this.store,
					displayInfo : true
				});
		this.callParent(arguments);
		this.getStore().load();
	}
});