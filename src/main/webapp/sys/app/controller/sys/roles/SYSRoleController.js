Ext.define('EJ.controller.sys.roles.SYSRoleController', {
	extend : 'Ext.app.Controller',
	customGlobal : {
		roleMenuRecords : null
	},
	models : ['sys.menu.SYSConfigMenuModel'],
	init : function() {
		this.control({
			'button[action=add-role-win]' : {
				click : this.showAddRoleWin
			},
			'addWinRole textfield[name=name]' : {
				blur : this.testName
			},
			'addWinRole textfield[name=code]' : {
				blur : this.testCode
			},
			'button[action=add-role]' : {
				click : this.add_update_Role
			},
			'sysRoleMain tabpanel' : {
				tabchange : this.setMenuData
			},
			'sysRoleList' : {
				itemdblclick : this.readRole,
				selectionchange : this.loadRoleResourceAndUsersAndMenusDatas
			},
			'button[action=show-editRoleWin-win]' : {
				click : this.showUpdateRoleWin
			},
			'button[action=update-role]' : {
				click : this.add_update_Role
			},
			'button[action=delete-role]' : {
				click : this.deleteRole
			},
			'button[action=show-config-role-resource-win]' : {
				click : this.showConfigRoleResourceWin
			},
			'button[action=submit-config-role-resource]' : {
				click : this.submitConfigRoleResource
			},
			'button[action=delete-config-role-resource]' : {
				click : this.deleteConfigRoleResource
			},
			'button[action=show-config-role-users-win]' : {
				click : this.showConfigRoleUsersWin
			},
			'button[action=find-users-no]' : {
				click : this.findUserNo
			},
			'sysRoleUsersConfigWin menuitem[action=find-users-general]' : {
				click : this.findUserGeneral
			},
			'sysRoleUsersConfigWin menuitem[action=find-users-vip]' : {
				click : this.findUserVip
			},
			'sysRoleUsersConfigWin menuitem[action=find-users-key]' : {
				click : this.findUserKey
			},
			'sysRoleUsersConfigWin menuitem[action=find-users-lock]' : {
				click : this.findUserLock
			},
			'sysRoleUsersConfigWin menuitem[action=find-users-start]' : {
				click : this.findUserStart
			},
			'sysRoleUsersConfigWin menuitem[action=find-users-stop]' : {
				click : this.findUserStop
			},
			'sysRoleUsersConfigWin button[action=roleConfig-search-user-byKeyWord]' : {
				click : this.searchUserBykeyWord
			},
			'button[action=submit-config-role-user]' : {
				click : this.submitConfigRoleUser
			},
			'button[action=delete-role-config-users]' : {
				click : this.deleteConfigRoleUsers
			},
			'button[action=save-config-role-menu]' : {
				click : this.submitConfigRoleMenus
			},
			'button[action=using-new-config-role-resource]' : {
				click : this.usingNewConfigRoleResource
			}
		});
	},
	usingNewConfigRoleResource : function() {
		Ext.Ajax.request({
					url : 'role/refreshresourcemap',
					scope : this,
					method : 'POST',
					success : function() {
						Ext.MessageBox.alert('提示', '系统新权限配置已启用!');
					},
					failure : function() {
						Ext.Msg.alert('提示', '启用配置失败！');
					}
				});
	},
	loadRoleResourceAndUsersAndMenusDatas : function(grid, record) {
		if (record && record.length == 1) {
			var roleId = record[0].getId();
			this.loadResData(roleId, 'sys.roles.SYSRoleResourceStore',
					'role/findresourcebyroleid');
			this.loadResData(roleId, 'sys.roles.SYSRoleUserStore',
					'role/findusersbyroleid');
			this.loadMenuData(roleId);
		}
	},
	showConfigRoleUsersWin : function() {
		var showWin = 'sys.roles.SYSRoleUsersConfigWin';
		var vstore = 'sys.roles.SYSRoleNoUserStore';
		var url = 'role/findnoconfigroleusers';
		this.showConfigRoleWin(vstore, url, showWin);
	},
	findUserNo : function() {
		var showWin = 'sys.roles.SYSRoleUsersConfigWin';
		var vstore = 'sys.roles.SYSRoleNoUserStore';
		var url = 'role/findnoconfigroleusers';
		this.showConfigRoleWin_Type(vstore, url, showWin);
	},
	findUserGeneral : function() {
		var showWin = 'sys.roles.SYSRoleUsersConfigWin';
		var vstore = 'sys.roles.SYSRoleNoUserStore';
		var url = 'role/findnoconfigroleusers?vip=0';
		this.showConfigRoleWin_Type(vstore, url, showWin);
	},
	findUserVip : function() {
		var showWin = 'sys.roles.SYSRoleUsersConfigWin';
		var vstore = 'sys.roles.SYSRoleNoUserStore';
		var url = 'role/findnoconfigroleusers?vip=1';
		this.showConfigRoleWin_Type(vstore, url, showWin);
	},
	findUserKey : function() {
		var showWin = 'sys.roles.SYSRoleUsersConfigWin';
		var vstore = 'sys.roles.SYSRoleNoUserStore';
		var url = 'role/findnoconfigroleusers?locked=0';
		this.showConfigRoleWin_Type(vstore, url, showWin);
	},
	findUserLock : function() {
		var showWin = 'sys.roles.SYSRoleUsersConfigWin';
		var vstore = 'sys.roles.SYSRoleNoUserStore';
		var url = 'role/findnoconfigroleusers?locked=1';
		this.showConfigRoleWin_Type(vstore, url, showWin);
	},
	findUserStart : function() {
		var showWin = 'sys.roles.SYSRoleUsersConfigWin';
		var vstore = 'sys.roles.SYSRoleNoUserStore';
		var url = 'role/findnoconfigroleusers?enabled=1';
		this.showConfigRoleWin_Type(vstore, url, showWin);
	},
	findUserStop : function() {
		var showWin = 'sys.roles.SYSRoleUsersConfigWin';
		var vstore = 'sys.roles.SYSRoleNoUserStore';
		var url = 'role/findnoconfigroleusers?enabled=0';
		this.showConfigRoleWin_Type(vstore, url, showWin);
	},
	searchUserBykeyWord : function(btn) {
		var showWin = 'sys.roles.SYSRoleUsersConfigWin';
		window.winRU = Ext.getCmp('SYSRoleUsersConfigWin');
		var keyWord = winRU.down('textfield[name=keyWord]').getValue();
		var vstore = 'sys.roles.SYSRoleNoUserStore';
		var url = 'role/findnoconfigroleusers?keyWord=' + keyWord;
		this.showConfigRoleWin_Type(vstore, url, showWin);
	},
	submitConfigRoleUser : function(btn) {
		var url = 'role/configroleusers';
		var rStore = 'sys.roles.SYSRoleUserStore';
		var idName = 'username';
		var configType = 'gridWindow';
		this.submitConfig(btn, null, url, rStore, idName, configType, false);
	},
	deleteConfigRoleUsers : function() {
		var gridPanelTabName = 'RoleUser';
		var url = 'role/removeroleuser';
		var rStore = 'sys.roles.SYSRoleUserStore';
		var idName = 'username';
		var configType = 'gridPanelTab';
		this.deleteConfigRoleAlert(null, gridPanelTabName, url, rStore, idName,
				configType, false);
	},
	showConfigRoleResourceWin : function() {
		var vstore = 'sys.roles.SYSRoleNoResourceStore';
		var url = 'role/findnoconfigroleresource';
		var showWin = 'sys.roles.SYSRoleResourcesConfigWin';
		this.showConfigRoleWin(vstore, url, showWin);
	},
	submitConfigRoleResource : function(btn) {
		var url = 'role/configroleresources';
		var rStore = 'sys.roles.SYSRoleResourceStore';
		var idName = 'id';
		var configType = 'gridWindow';
		this.submitConfig(btn, null, url, rStore, idName, configType, false);
	},
	deleteConfigRoleResource : function() {
		var gridPanelTabName = 'SYSRoleResourcesTab';
		var url = 'role/removeroleresource';
		var rStore = 'sys.roles.SYSRoleResourceStore';
		var idName = 'id';
		var configType = 'gridPanelTab';
		this.deleteConfigRoleAlert(null, gridPanelTabName, url, rStore, idName,
				configType, false);
	},
	submitConfigRoleMenus : function() {
		var gridPanelTabName = 'RoleMenuTab';
		var url = 'role/configrolemenus';
		var rStore = 'sys.menu.SYSConfigMenuTreeStore';
		var idName = 'id';
		var configType = 'gridPanelTab';
		this.submitConfig(null, gridPanelTabName, url, rStore, idName,
				configType, true);
	},
	deleteConfigRoleAlert : function(btn, gridPanelTabName, url, rStore,
			idName, configType, menusLoadBoleean) {
		Ext.Msg.confirm('提示', '确定要删除所选内容?', function(v) {
					if (v == 'yes') {
						this.submitConfig(btn, gridPanelTabName, url, rStore,
								idName, configType, menusLoadBoleean);
					}
				}, this);
	},
	/*
	 * btn:window窗口提交时用, gridPanelTabName：gridPanel页ID值, url：请求方法路径,
	 * rStore：提交页面所用的数据源Store,
	 * idName：取提交页面上数据ID名,menusLoadBoleean:true配置MENUS后调用loadMenuData(roleId)
	 */
	submitConfig : function(btn, gridPanelTabName, url, rStore, idName,
			configType, menusLoadBoleean) {
		if (configType == 'gridWindow') {
			var win = btn.up('window');
			var selectedRecords = win.down('grid').getSelectionModel()
					.getSelection();
		} else if (configType == 'gridPanelTab') {
			window.SYSRoleConfigTab = Ext.getCmp(gridPanelTabName);
			var selectedRecords = SYSRoleConfigTab.getSelectionModel()
					.getSelection();
		} else {
			return;
		}
		var selections = this.roleListSelection();
		if (!selections || selections.length != 1) {
			Ext.Msg.show({
						title : '操作错误',
						msg : '请在左侧角色列表中选中一个角色进行操作!',
						icon : Ext.Msg.ERROR,
						buttons : Ext.Msg.OK
					});
			return;
		}
		var roleId = selections[0].getId();
		if (selectedRecords && selectedRecords.length > 0) {
			var ids = [];
			for (var i = 0; i < selectedRecords.length; i++) {
				ids.push(selectedRecords[i].get(idName));
			}
			Ext.Ajax.request({
						url : url,
						scope : this,
						method : 'POST',
						params : {
							ids : ids,
							roleId : roleId
						},
						success : function() {
							if (configType == 'gridWindow') {
								win.close();
							}
							if (menusLoadBoleean) {
								Ext.MessageBox.alert('提示', '菜单配置成功！');
							} else {
								this.getStore(rStore).load();
							}
						},
						failure : function() {
							Ext.Msg.alert('提示', '菜单配置失败！');
						}
					});
		} else {
			Ext.Msg.show({
						title : '提示',
						msg : '您尚未选择资源!',
						icon : Ext.Msg.INFO,
						buttons : Ext.Msg.OK
					});
		}
	},
	showConfigRoleWin : function(vstore, url, showWin, type) {
		var selections = this.roleListSelection();
		if (!selections || selections.length != 1) {
			Ext.Msg.show({
						title : '操作错误',
						msg : '请在左侧角色列表中选中一个角色进行操作!',
						icon : Ext.Msg.ERROR,
						buttons : Ext.Msg.OK
					});
			return;
		}
		var roleId = selections[0].getId();
		var store = this.getStore(vstore);
		store.proxy.url = url;
		store.proxy.extraParams = {
			roleId : roleId
		};
		store.load();
		this.getView(showWin).create().show();
	},
	showConfigRoleWin_Type : function(vstore, url, showWin, type) {
		var selections = this.roleListSelection();
		if (!selections || selections.length != 1) {
			Ext.Msg.show({
						title : '操作错误',
						msg : '请在左侧角色列表中选中一个角色进行操作!',
						icon : Ext.Msg.ERROR,
						buttons : Ext.Msg.OK
					});
			return;
		}
		var roleId = selections[0].getId();
		var store = this.getStore(vstore);
		store.proxy.url = url;
		store.proxy.extraParams = {
			roleId : roleId
		};
		store.load();
	},
	setMenuData : function(tab, item) {
		if (item.id == 'RoleMenuTab' && this.customGlobal.roleMenuRecords) {
			var sm = item.getSelectionModel();
			sm.select(this.customGlobal.roleMenuRecords, false);
		}
	},
	loadMenuData : function(roleId) {
		Ext.Ajax.request({
					scope : this,
					url : 'role/findroletreebyroleid',
					params : {
						roleId : roleId
					},
					success : function(response) {
						var records = Ext.decode(response.responseText);
						window.RoleMenuTab = Ext.getCmp('RoleMenuTab');
						var sm = RoleMenuTab.getSelectionModel();
						var sr = [];
						for (var i = 0; i < records.length; i++) {
							var menu = EJ.model.sys.menu.SYSConfigMenuModel
									.create(records[i]);
							sr.push(menu);
						}
						this.customGlobal.roleMenuRecords = sr;
						var activeItem = Ext.getCmp('roleTabs').getActiveTab();
						if (activeItem.title == '菜单分配') {
							sm.select(sr, false);
						}
					}
				});
	},
	loadResData : function(roleId, stores, url) {
		var store = this.getStore(stores);
		store.proxy.url = url;
		store.proxy.extraParams = {
			roleId : roleId
		};
		store.load();
	},
	deleteRole : function() {
		Ext.Msg.confirm('提示', '确定要删除该角色?', function(v) {
					if (v == 'yes') {
						this.deleteROLE();
					}
				}, this);
	},
	deleteROLE : function() {
		var role = this.roleListSelection()[0];
		role.destroy({
					scope : this,
					success : function() {
						this.getStore('sys.roles.SYSRoleStore').reload();
					},
					failure : function() {
						Ext.Msg.alert('提示', '删除失败！');
					}
				});
	},
	showUpdateRoleWin : function() {
		var selections = this.roleListSelection();
		var selecForm = selections[0];
		var view = Ext.widget('editRoleWin');
		view.down('form').loadRecord(selecForm);
	},
	readRole : function(grid, record) {
		var view = Ext.widget('roleInfoWin');
		view.down('form').loadRecord(record);
	},
	showAddRoleWin : function() {
		this.getView('sys.roles.AddWin').create().show();
	},
	testName : function(btn) {
		var name = btn.getValue();
		Ext.Ajax.request({
			url : 'role/exists',
			scope : this,
			params : {
				name : name
			},
			success : function(response) {
				var ps = Ext.decode(response.responseText);
				if (ps) {
					Ext.MessageBox.show({
						title : '警告',
						msg : '角色名:<font color=blue> ' + name + '</font> 已存在!',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING,
						fn : function() {
							btn.up('form').down('textfield[name=name]').reset();
						}
					});
				}
			}
		});
	},
	testCode : function(btn) {
		var code = btn.getValue();
		Ext.Ajax.request({
			url : 'role/exists',
			scope : this,
			params : {
				code : code
			},
			success : function(response) {
				var ps = Ext.decode(response.responseText);
				if (ps) {
					Ext.MessageBox.show({
						title : '警告',
						msg : '角色CODE:<font color=blue> ' + code
								+ '</font> 已存在!',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING,
						fn : function() {
							btn.up('form').down('textfield[name=code]').reset();
						}
					});
				}
			}
		});
	},
	add_update_Role : function(btn) {
		var form = btn.up('window').down('form').getForm();
		if (form.isValid) {
			var v = form.getValues();
			var role = EJ.model.sys.roles.SYSRoleModel.create(v);
			role.save({
				scope : this,
				success : function() {
					btn.up('window').close();
					this.getStore('sys.roles.SYSRoleStore').reload();
					Ext.getCmp('SYSRoleList').getSelectionModel().deselectAll();
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败！');
				}
			});
		}
	},
	roleListSelection : function() {
		window.SYSRoleList = Ext.getCmp('SYSRoleList');
		return SYSRoleList.getSelectionModel().getSelection();
	}
});