Ext.define('EJ.view.sys.user.EndVipUserList', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.endVipUserList',// 首页VIP到期显示
	title : '已到期的VIP用户',
	id : 'EndVipUserList',
	store : 'sys.user.EndVipUserStore',
	columnLines : true,
	initComponent : function() {
		// this.selModel = Ext.create('Ext.selection.CheckboxModel');
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
		this.tbar = [{
					text : '用户权限调整',
					icon : './resources/img/vipuser-user.png',
					disabled : true,
					action : 'vipuser-transition-user'
				}, {
					text : '启用配置',
					icon : './resources/img/saved.png',
					action : 'using-new-config-role-resource'
				}, '->', '关键词检索:', {
					xtype : 'textfield',
					name : 'keyWord',
					hideLabel : true,
					emptyText : '请输入关键词',
					width : 130
				}, {
					icon : './resources/img/serchMenu.png',
					text : '搜',
					action : 'search-endvipuser'
				}];
		this.callParent(arguments);
		this.on('selectionchange', function(sm, selections) {
					this.down('button[action=vipuser-transition-user]')
							.setDisabled(!selections.length > 0);
				});
		this.getStore().load();
	}
});