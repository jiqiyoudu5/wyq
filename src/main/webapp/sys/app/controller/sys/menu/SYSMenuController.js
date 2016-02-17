Ext.define('EJ.controller.sys.menu.SYSMenuController', {
	extend : 'Ext.app.Controller',
	init : function() {
		this.control({
					'button[action=add-menu-win]' : {
						click : this.showAddMenuWin
					},
					'sysAddMenu textfield[name=value]' : {
						blur : this.testValue
					},
					'button[action=add-treeMenu]' : {
						click : this.add_update_TreeMenu
					},
					'button[action=delete-menu]' : {
						click : this.deleteTreeMenu
					},
					'button[action=show-editMenu-win]' : {
						click : this.showEditMenuWin
					},
					"sysEditMenu" : {
						"afterrender" : function() {
							this.getStore("sys.file.SYSMenuIcon").load();
						}
					},
					'button[action=update-treeMenu]' : {
						click : this.add_update_TreeMenu
					}
				});
	},
	showEditMenuWin : function() {
		window.SYSTreeList = Ext.getCmp('SYSTreeList');
		var selections = SYSTreeList.getSelectionModel().getSelection();
		var selecForm = selections[0];
		var menu = EJ.model.sys.menu.SYSConfigMenuModel.create(selecForm.data);
		// menu.set('icon', selecForm.data.icon.substring(9));
		menu.set('icon', selecForm.get('icon').substring(9));
		var view = Ext.widget('sysEditMenu');
		view.down('form').loadRecord(menu);
	},
	testValue : function(b) {
		var value = b.getValue();
		Ext.Ajax.request({
			url : 'menu/exists',
			scope : this,
			params : {
				value : value
			},
			success : function(response) {
				var ps = Ext.decode(response.responseText);
				if (ps) {
					Ext.MessageBox.show({
						title : '警告',
						msg : '<font color=blue> ' + value + '</font> 已指向其它菜单!',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING,
						fn : function() {
							b.up('form').down('textfield[name=value]').reset();
						}
					});
				}
			}
		});
	},
	deleteTreeMenu : function(btn) {
		window.SYSTreeList = Ext.getCmp('SYSTreeList');
		var menu = SYSTreeList.getSelectionModel().getSelection()[0];
		var id = menu.get('id');
		var msg;
		if (id === '402883e043330ab50143330ab8920001') {
			alert('禁止删除该菜单项！');
			return false;
		} else if (!menu.raw.leaf) {
			msg = '该菜单为根节点，在删除该菜单后，子菜单将会被移动到菜单总节点下，您是否确认要这么做?';
		} else {
			msg = '确定要删除该菜单?';
		}
		Ext.Msg.confirm('提示', msg, function(v) {
					if (v == 'yes') {
						this.deleteMenu(menu);
					}
				}, this);
	},
	deleteMenu : function(menu) {
		// 由于list中不是全model字段所有这里要申明个model对象m
		var m = new EJ.model.sys.menu.SYSConfigMenuModel();
		m.set('id', menu.get('id'));
		m.destroy({
					scope : this,
					success : function() {
						this.getStore('sys.menu.SYSConfigMenuTreeStore')
								.reload();
					},
					failure : function() {
						Ext.Msg.alert('提示', '删除失败！');
					}
				});
	},
	add_update_TreeMenu : function(btn) {
		window.SYSTreeList = Ext.getCmp('SYSTreeList');
		var selections = SYSTreeList.getSelectionModel().getSelection();
		var pid = selections[0].get('id');
		var form = btn.up("form").getForm();
		if (form.isValid()) {
			var v = form.getValues();
			var menu = EJ.model.sys.menu.SYSConfigMenuModel.create(v);
			menu.set('pid', pid);
			console.log(menu);
			menu.save({
				scope : this,
				success : function() {
					btn.up('window').close();
					this.getStore('sys.menu.SYSConfigMenuTreeStore').reload();
					Ext.getCmp('SYSTreeList').getSelectionModel().deselectAll();
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败！');
				}
			});

		}
	},
	showAddMenuWin : function() {
		this.getView('sys.menu.SYSAddMenu').create().show();
	}
});