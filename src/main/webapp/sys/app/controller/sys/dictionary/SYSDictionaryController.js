Ext.define('EJ.controller.sys.dictionary.SYSDictionaryController', {
	extend : 'Ext.app.Controller',
	init : function() {
		this.control({
			'sysDicList button[action=show_add_win]' : {
				click : function(b) {
					var selections = this.initgetSelectionDic('SYSDicTreeList');
					if (!selections || selections.length != 1) {
						Ext.Msg.show({
									title : '操作错误',
									msg : '请在左侧字典库中选中一个字典项再进行操作!',
									icon : Ext.Msg.ERROR,
									buttons : Ext.Msg.OK
								});
						return;
					}
					var selection = selections[0];
					if (selection.get('type') === 'DICTYPE') {
						this.getView('sys.dictionary.AddDicTypeWin').create()
								.show();
					} else {
						this.getView('sys.dictionary.AddDicWin').create()
								.show();
					}
				}
			},
			'addSYSDicTypeWin textfield[name=text]' : {
				blur : function(b) {
					this.testText(b, null, false);
				}
			},
			'addSYSDicTypeWin textfield[name=type]' : {
				blur : function(b) {
					this.testType(b, 'SYSDicTreeList', false);
				}
			},
			'addSYSDicWin textfield[name=text]' : {
				blur : function(b) {
					this.testText(b, null, false);
				}
			},
			'addSYSDicTypeWin button[action=add]' : {
				click : this.add_Dic
			},
			'addSYSDicWin button[action=add]' : {
				click : this.add_Dic
			},
			'sysDicTreeList' : {
				selectionchange : this.loadDics
			},
			'sysDicList button[action=show_update_win]' : {
				click : function() {
					var selection = this.initgetSelectionDic('SYSDicList')[0];
					var win = null;
					if (selection.get('parent') === 'wyq8d8i8c8t8y8p8e') {
						win = 'updateDicTypeWin';
					} else {
						win = 'updateDicWin';
					}
					this.showFormWin('SYSDicList', win);
				}
			},
			'updateDicTypeWin textfield[name=type]' : {
				blur : function(b) {
					this.testType(b, 'SYSDicList', true);
				}
			},
			'updateDicTypeWin textfield[name=text]' : {
				blur : function(b) {
					this.testText(b, 'SYSDicList', true);
				}
			},
			'updateDicTypeWin button[action=update]' : {
				click : this.update_Dic
			},
			'updateDicWin textfield[name=text]' : {
				blur : function(b) {
					this.testText(b, 'SYSDicList', true);
				}
			},
			'updateDicWin button[action=update]' : {
				click : this.update_Dic
			},
			'sysDicList button[action=delete]' : {
				click : function(b) {
					var selectForm = this.initgetSelectionDic('SYSDicList')[0];
					var parent = selectForm.get('parent');
					if (parent === 'wyq8d8i8c8t8y8p8e') {
						Ext.Msg
								.confirm(
										'询问',
										'删除字典  <strong><font style="font-size:16px; color:#FF0000;">根节点</font></strong> 时也将删除其子项字典?',
										function(v) {
											if (v == 'yes') {
												this.deleteDic(b, 'DICTYPE');
											}
										}, this);
					} else {
						Ext.Msg
								.confirm(
										'询问',
										'确定删除  <strong><font style="font-size:16px; color:#FF0000;">所选</font></strong> 字典?',
										function(v) {
											if (v == 'yes') {
												this.deleteDic(b, null);
											}
										}, this);
					}
				}
			},
			'sysDicList button[action=search_byKeyWord]' : {
				click : this.searchDicsByKeyWord
			}
		});
	},
	add_Dic : function(btn) {
		var form = btn.up('window').down('form').getForm();
		if (form.isValid()) {
			var v = form.getValues();
			var model = EJ.model.sys.dictionary.SYSDicModel.create(v);
			var select = this.initgetSelectionDic('SYSDicTreeList')[0];
			var type = select.get('type');
			var parentcode = select.get('id');
			if (type === 'DICTYPE') {
				model.set('parent', parentcode);
			} else {
				model.set('type', type);
				model.set('parent', parentcode);
			}
			model.save({
						scope : this,
						success : function() {
							btn.up('window').close();
							this.getStore('sys.dictionary.SYSDicTreeStore')
									.reload();
							this.getStore('sys.dictionary.SYSDicStore')
									.reload();
						},
						failure : function() {
							Ext.Msg.alert('提示', '操作失败！');
						}
					});
		}
	},
	showFormWin : function(gridList, win) {
		var selectForm = this.initgetSelectionDic(gridList)[0];
		var view = Ext.widget(win);
		view.down('form').loadRecord(selectForm);
	},
	initgetSelectionDic : function(gridList) {
		window.list = Ext.getCmp(gridList);
		return list.getSelectionModel().getSelection();
	},
	loadDics : function(grid, record) {
		if (record && record.length == 1) {
			var id = record[0].getId();
			var store = this.getStore('sys.dictionary.SYSDicStore');
			store.proxy.url = 'dictionary/findbypage';
			store.proxy.extraParams = {
				parentCode : id
			};
			store.load();
		}
	},
	testText : function(b, gridList, isUpdate) {
		var text = b.getValue();
		if (isUpdate) {
			var selectForm = this.initgetSelectionDic(gridList)[0];
			var textOld = selectForm.get('text');
			if (text == textOld) {
				return;
			}
		}
		Ext.Ajax.request({
			url : 'dictionary/exists',
			scope : this,
			params : {
				text : text
			},
			success : function(response) {
				var ps = Ext.decode(response.responseText);
				if (ps) {
					Ext.MessageBox.show({
						title : '警告',
						msg : '字典名称:<font color=blue> ' + text + '</font> 已存在!',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING,
						fn : function() {
							b.up('form').down('textfield[name=text]').reset();
						}
					});
				}
			}
		});
	},
	testType : function(b, gridList, isUpdate) {
		var type = b.getValue();
		if (isUpdate) {
			var selectForm = this.initgetSelectionDic(gridList)[0];
			var typeOld = selectForm.get('type');
			if (type == typeOld) {
				return;
			}
		}
		Ext.Ajax.request({
			url : 'dictionary/exists',
			scope : this,
			params : {
				type : type
			},
			success : function(response) {
				var ps = Ext.decode(response.responseText);
				if (ps) {
					Ext.MessageBox.show({
						title : '警告',
						msg : '字典类别:<font color=blue> ' + type + '</font> 已存在!',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING,
						fn : function() {
							b.up('form').down('textfield[name=type]').reset();
						}
					});
				}
			}
		});
	},
	update_Dic : function(btn) {
		var form = btn.up('window').down('form').getForm();
		if (form.isValid()) {
			var v = form.getValues();
			var model = EJ.model.sys.dictionary.SYSDicModel.create(v);
			model.save({
						scope : this,
						success : function() {
							btn.up('window').close();
							this.getStore('sys.dictionary.SYSDicTreeStore')
									.reload();
							this.getStore('sys.dictionary.SYSDicStore')
									.reload();
							Ext.getCmp('SYSDicList').getSelectionModel()
									.deselectAll();
						},
						failure : function() {
							Ext.Msg.alert('提示', '操作失败！');
						}
					});
		}
	},
	deleteDic : function(b, type) {
		var selections = this.initgetSelectionDic('SYSDicList');
		if (selections && selections.length > 0) {
			var ids = [];
			for (var i = 0; i < selections.length; i++) {
				ids.push(selections[i].get('id'));
			}
			Ext.Ajax.request({
						url : 'dictionary/delete',
						scope : this,
						params : {
							type : type,
							ids : ids
						},
						success : function() {
							this.getStore('sys.dictionary.SYSDicTreeStore')
									.reload();
							this.getStore('sys.dictionary.SYSDicStore')
									.reload();
						},
						failure : function() {
							Ext.Msg.alert('提示', '操作失败！');
						}
					});
		} else {
			Ext.Msg.show({
						title : '提示',
						msg : '您尚未选择内容!',
						icon : Ext.Msg.INFO,
						buttons : Ext.Msg.OK
					});
		}
	},
	searchDicsByKeyWord : function(btn) {
		window.list = Ext.getCmp('SYSDicList');
		var keyWord = list.down('textfield[name=keyWord]').getValue();
		var selections = this.initgetSelectionDic('SYSDicTreeList');
		if (!selections || selections.length != 1) {
			Ext.Msg.show({
						title : '操作错误',
						msg : '请在左侧字典库中选中一个字典项再在旗下进行检索操作!',
						icon : Ext.Msg.ERROR,
						buttons : Ext.Msg.OK
					});
			return;
		}
		var selectForm = selections[0];
		var id = selectForm.get('id');
		this.getStore('sys.dictionary.SYSDicStore').load({
					url : 'dictionary/findbypage',
					params : {
						keyWord : keyWord,
						parentCode : id
					}
				});
	}

});