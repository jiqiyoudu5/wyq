Ext.define('EJ.controller.sys.resource.SYSResourceController', {
	extend : 'Ext.app.Controller',
	init : function() {
		this.control({
					'button[action=show-editWin-win]' : {
						click : this.showUpdateResource
					},
					'button[action=udpate-resource]' : {
						click : this.add_update_Resource
					},
					'button[action=add-resource-win]' : {
						click : this.showAddResourceWin
					},
					'addWinResource textfield[name=url]' : {
						blur : this.testUrl
					},
					'button[action=add-resource]' : {
						click : this.add_update_Resource
					},
					'button[action=delete-resource]' : {
						click : this.deleteResource
					},
					'button[action=search-by-resourceName]' : {
						click : this.searchResource
					}
				});
	},
	searchResource : function(btn) {
		var values = Ext.getCmp('SearchForm').getForm().getValues();
		this.getStore('sys.resource.SYSResourceStore').load({
					url : 'resource/findall',
					params : values
				});
	},
	testUrl : function(b) {
		var url = b.getValue();
		Ext.Ajax.request({
			url : 'resource/exists',
			scope : this,
			params : {
				url : url
			},
			success : function(response) {
				var ps = Ext.decode(response.responseText);
				if (ps) {
					Ext.MessageBox.show({
						title : '警告',
						msg : '资源URL:<font color=blue> ' + url + '</font> 已存在!',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING,
						fn : function() {
							b.up('form').down('textfield[name=url]').reset();
						}
					});
				}
			}
		});
	},
	deleteResource : function() {
		Ext.Msg.confirm('提示', '确定要删除该资源?', function(v) {
					if (v == 'yes') {
						this.deleteResourceInfo();
					}
				}, this);
	},
	deleteResourceInfo : function() {
		window.SYSResourceList = Ext.getCmp('SYSResourceList');
		var resource = SYSResourceList.getSelectionModel().getSelection()[0];
		resource.destroy({
					scope : this,
					success : function() {
						this.getStore('sys.resource.SYSResourceStore').reload();
					},
					failure : function() {
						Ext.Msg.alert('提示', '删除失败！');
					}
				});
	},
	add_update_Resource : function(btn) {
		var form = btn.up('window').down('form').getForm();
		if (form.isValid()) {
			var v = form.getValues();
			var resource = EJ.model.sys.resource.SYSResourceModel.create(v);
			resource.save({
						scope : this,
						success : function() {
							btn.up('window').close();
							this.getStore('sys.resource.SYSResourceStore')
									.reload();
							Ext.getCmp('SYSResourceList').getSelectionModel()
									.deselectAll();
						},
						failure : function() {
							Ext.Msg.alert('提示', '操作失败！');
						}
					});
		}
	},
	showAddResourceWin : function() {
		this.getView('sys.resource.AddWin').create().show();
	},
	showUpdateResource : function() {
		window.SYSResourceList = Ext.getCmp('SYSResourceList');
		var selections = SYSResourceList.getSelectionModel().getSelection();
		var selecForm = selections[0];
		var view = Ext.widget('editResWin');
		view.down('form').loadRecord(selecForm);
	}
});