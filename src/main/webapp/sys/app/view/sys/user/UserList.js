Ext.define('EJ.view.sys.user.UserList', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.sysUserList',// 别名
	id : 'SYSUserList',
	store : 'sys.user.UserStore',
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
					renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
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
					text : '注册新用户',
					icon : './resources/img/add.png',
					action : 'show-regist-user-win'
				}, {
					text : '修改用户',
					xtype : 'splitbutton',
					icon : './resources/img/edit.png',
					reorderable : false,
					menu : [{
								text : '修改密码',
								icon : './resources/img/edit.png',
								action : 'update-user-password-win'
							}, {
								text : '详细信息修改',
								icon : './resources/img/edit.png',
								action : 'update-user-win'
							}],
					disabled : true,
					action : 'update'
				}, {
					text : '删除用户',
					icon : './resources/img/delete.png',
					disabled : true,
					action : 'delete-user'
				}, {
					text : '分类管理',
					xtype : 'splitbutton',
					icon : './resources/img/users.png',
					reorderable : false,
					menu : [{
								text : 'VIP用户',
								icon : './resources/img/vip.png',
								action : 'find-users-vip'
							}, {
								text : '普通用户',
								icon : './resources/img/user.png',
								action : 'find-users-general'
							}, {
								text : '解锁用户',
								icon : './resources/img/key.png',
								action : 'find-users-key'
							}, {
								text : '锁定用户',
								icon : './resources/img/lock.png',
								action : 'find-users-lock'
							}, {
								text : '启用用户',
								icon : './resources/img/saved.png',
								action : 'find-users-start'
							}, {
								text : '暂停用户',
								icon : './resources/img/stop.png',
								action : 'find-users-stop'
							}],
					action : 'find-users'
				}, {
					text : '查询',
					icon : './resources/img/serchMenu.png',
					action : 'search-user-win'
				}, '->', '关键词检索:', {
					xtype : 'textfield',
					name : 'keyWord',
					hideLabel : true,
					emptyText : '请输入关键词',
					width : 130
				}, {
					icon : './resources/img/serchMenu.png',
					text : '搜',
					action : 'search-user-byKeyWord'
				}];

		this.callParent(arguments);
		this.on('selectionchange', function(sm, selections) {
					this.down('button[action=delete-user]')
							.setDisabled(!selections.length > 0);
					this.down('button[action=update]')
							.setDisabled(!selections.length > 0);
				});

		// 加载此页时执行store中方法获取数据
		this.getStore().reload();
	}
});