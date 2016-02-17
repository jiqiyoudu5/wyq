Ext.define('EJ.view.sys.roles.SYSRoleUsersConfigWin', {
	extend : 'Ext.window.Window',
	title : '分配新用户',
	width : 500,
	height : 400,
	modal : true,
	border : false,
	alias : 'widget.sysRoleUsersConfigWin',
	id : 'SYSRoleUsersConfigWin',
	store : 'sys.roles.SYSRoleNoUserStore',
	layout : 'fit',
	initComponent : function() {
		var sm = Ext.create('Ext.selection.CheckboxModel');
		this.items = [{
			xtype : 'grid',
			id : 'configRoleUsers',
			selModel : sm,
			store : this.store,
			columnLines : true,
			columns : [{
						text : '用户名',
						dataIndex : 'username',
						flex : 1
					}, {
						text : '公司名称',
						dataIndex : 'businessName',
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
					}],
			bbar : Ext.create('Ext.PagingToolbar', {
						store : this.store,
						displayInfo : true
					})
		}];
		this.buttons = [{
					text : '确定',
					action : 'submit-config-role-user'
				}, {
					text : '取消',
					scope : this,
					handler : this.close
				}];
		this.tbar = [{
					text : '分类查看',
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
					action : 'find-users-no'
				}, '->', '关键词检索:', {
					xtype : 'textfield',
					name : 'keyWord',
					hideLabel : true,
					emptyText : '请输入关键词',
					width : 130
				}, {
					icon : './resources/img/serchMenu.png',
					text : '搜',
					action : 'roleConfig-search-user-byKeyWord'
				}];
		this.callParent(arguments);
	}

});