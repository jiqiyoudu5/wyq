Ext.define('EJ.controller.sys.user.UserController', {
	extend : 'Ext.app.Controller',
	init : function() {
		// init函数通过this.control来负责监听
		this.control({
					'button[action=search-user-win]' : {
						click : this.searchUserWin
					},
					'button[action=search-user]' : {
						click : this.searchUser
					},
					'button[action=find-users]' : {
						click : this.findall
					},
					'menuitem[action=find-users-general]' : {
						click : this.findUserGeneral
					},
					'menuitem[action=find-users-vip]' : {
						click : this.findUserVip
					},
					'menuitem[action=find-users-key]' : {
						click : this.findUserKey
					},
					'menuitem[action=find-users-lock]' : {
						click : this.findUserLock
					},
					'menuitem[action=find-users-start]' : {
						click : this.findUserStart
					},
					'menuitem[action=find-users-stop]' : {
						click : this.findUserStop
					},
					'button[action=show-regist-user-win]' : {
						click : this.showRegistUserWin
					},
					'sysUserList' : {
						itemdblclick : this.readUser
					},
					'expireVipUserList' : {
						itemdblclick : this.readUser
					},
					'endVipUserList' : {
						itemdblclick : this.readUser
					},
					'registUserForm textfield[name=username]' : {
						blur : this.testUsername
					},
					'button[action=reg-user]' : {
						click : this.submitRegUserForm
					},
					'button[action=delete-user]' : {
						click : this.deleteUser
					},
					'menuitem[action=update-user-win]' : {
						click : this.updateUserWin
					},
					'button[action=udpate-user]' : {
						click : this.updateUser
					},
					'menuitem[action=update-user-password-win]' : {
						click : this.updatePasswordWin
					},
					'button[action=update-user-password]' : {
						click : this.updatePassword
					},
					'button[action=vipuser-transition-user]' : {
						click : this.vipuserTransitionUser
					},
					'button[action=remove-user-roles]' : {
						click : this.removeUserRoles
					},
					'button[action=search-user-byKeyWord]' : {
						click : this.searchUserBykeyWord
					},
					'button[action=search-endvipuser]' : {
						click : this.searchEndVipUser
					}
				});
	},
	searchEndVipUser : function(btn) {
		window.EndVipUserList = Ext.getCmp('EndVipUserList');
		var keyWord = EndVipUserList.down('textfield[name=keyWord]').getValue();// 获取页面上某个文本框值
		this.getStore('sys.user.EndVipUserStore').load({
					url : 'user/searchusersbykeyword',
					params : {
						keyWord : keyWord
					}
				});
	},
	searchUserBykeyWord : function(btn) {
		window.SYSUserList = Ext.getCmp('SYSUserList');
		var keyWord = SYSUserList.down('textfield[name=keyWord]').getValue();
		this.getStore('sys.user.UserStore').load({
					url : 'user/searchusersbykeyword',
					params : {
						keyWord : keyWord
					}
				});
	},
	vipuserTransitionUser : function(b) {
		window.EndVipUserList = Ext.getCmp('EndVipUserList');
		var selectedRecords = EndVipUserList.getSelectionModel().getSelection();
		var username = selectedRecords[0].get('username');
		this.getStore('sys.roles.SYSUserRoleStore').load({
					params : {
						username : username
					}
				});
		this.getView('sys.user.RemoveUserRolesWin').create().show();
	},
	removeUserRoles : function(b) {
		var value = b.up('window').down('form').getValues();
		window.EndVipUserList = Ext.getCmp('EndVipUserList');
		var selectedRecords = EndVipUserList.getSelectionModel().getSelection();
		var username = selectedRecords[0].get('username');
		Ext.Ajax.request({
					url : 'user/removevippower',
					scope : this,
					params : {
						ids : value,
						username : username
					},
					success : function() {
						b.up('window').close();
						Ext.MessageBox.alert('提示', '操作成功！');
						this.getStore('sys.user.EndVipUserStore').load();
					},
					failure : function() {
						Ext.Msg.alert('提示', '操作失败！');
					}
				});
	},
	searchUserWin : function() {
		this.getView('sys.user.SearchUserWin').create().show();
	},
	searchUser : function(btn) {
		var values = btn.up('window').down('form').getValues();
		this.getStore('sys.user.UserStore').load({
					url : 'user/query',
					params : values
				});
	},
	findall : function(b) {
		var store = this.getStore('sys.user.UserStore');
		store.proxy.url = 'user/findall';
		store.load();
	},
	findUserGeneral : function(b) {
		var store = this.getStore('sys.user.UserStore');
		store.proxy.url = 'user/findall?vip=0';
		store.load();
	},
	findUserVip : function(b) {
		var store = this.getStore('sys.user.UserStore');
		store.proxy.url = 'user/findall?vip=1';
		store.load();
	},
	findUserKey : function(b) {
		var store = this.getStore('sys.user.UserStore');
		store.proxy.url = 'user/findall?locked=0';
		store.load();
	},
	findUserLock : function(b) {
		var store = this.getStore('sys.user.UserStore');
		store.proxy.url = 'user/findall?locked=1';
		store.load();
	},
	findUserStart : function(b) {
		var store = this.getStore('sys.user.UserStore');
		store.proxy.url = 'user/findall?enabled=1';
		store.load();
	},
	findUserStop : function(b) {
		var store = this.getStore('sys.user.UserStore');
		store.proxy.url = 'user/findall?enabled=0';
		store.load();
	},
	updatePasswordWin : function() {
		window.SYSUserList = Ext.getCmp('SYSUserList');
		var selections = SYSUserList.getSelectionModel().getSelection();
		var selecForm = selections[0];
		var view = Ext.widget('modifcationUserPassword');
		view.down('form').loadRecord(selecForm);
	},
	updatePassword : function(b) {
		var form = b.up('window').down('form').getForm();
		form.submit({
					url : 'user/updatepassword',
					actionMethods : 'POST',
					scope : this,
					success : function() {
						b.up('window').close();
						this.getStore('sys.user.UserStore').reload();
					},
					failure : function() {
						Ext.Msg.alert('提示', '修改失败！');
					}
				});
	},
	updateUserWin : function() {
		window.SYSUserList = Ext.getCmp('SYSUserList');
		var selections = SYSUserList.getSelectionModel().getSelection();
		var selecForm = selections[0];
		var view = Ext.widget('modifcationUserWin');
		view.down('form').loadRecord(selecForm);
	},
	updateUser : function(b) {
		var form = b.up('window').down('form').getForm();
		form.submit({
					url : 'user/update',
					actionMethods : 'POST',
					scope : this,
					success : function() {
						b.up('window').close();
						this.getStore('sys.user.UserStore').reload();
						Ext.getCmp('SYSUserList').getSelectionModel()
								.deselectAll();
					},
					failure : function() {
						Ext.Msg.alert('提示', '修改失败！');
					}
				});
	},
	deleteUser : function() {
		Ext.Msg.confirm('提示', '是否确定要删除该用户?', function(v) {
					if (v == 'yes') {
						this.deleteUserInfo();
					}
				}, this);
	},
	deleteUserInfo : function() {
		window.SYSUserList = Ext.getCmp('SYSUserList');
		var user = SYSUserList.getSelectionModel().getSelection()[0];
		user.destroy({
					scope : this,
					success : function() {
						this.getStore('sys.user.UserStore').reload();
					},
					failure : function() {
						Ext.Msg.alert('提示', '删除失败！');
					}
				});
	},
	showRegistUserWin : function() {
		this.getView('sys.user.RegistUserWin').create().show();
	},
	readUser : function(grid, record) {
		var view = Ext.widget('userInfoWin');
		view.down('form').loadRecord(record);
	},
	testUsername : function(b) {// 添加用户时判断该用户名是否存在
		var username = b.getValue();
		Ext.Ajax.request({
					url : 'user/exists',
					scope : this,
					params : {
						username : username
					},
					success : function(response) {
						var ps = Ext.decode(response.responseText);
						if (ps) {

							Ext.MessageBox.show({
										title : '警告',
										msg : '用户名<font color=blue> '
												+ username + '</font> 已存在!',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.WARNING,
										fn : function() {
											b
													.up('form')
													.down('textfield[name=username]')
													.reset();
										}
									});
						}
					}
				});
	},
	submitRegUserForm : function(b) {
		var form = b.up("form").getForm();
		if (form.isValid()) {
			var v = form.getValues();
			var user = EJ.model.sys.user.UserModel.create(v);
			user.phantom = true;// 主键名不为id时用（model另外声明主键）
			user.save({
						scope : this,
						success : function() {
							b.up('window').close();
							this.getStore('sys.user.UserStore').reload();
							this.getComponent("sysUserList")
									.getSelectionModel().deselectAll();
						},
						failure : function(a, b) {
							Ext.Msg.alert('提示', b.request.message);
						}
					});
		}
	}

});