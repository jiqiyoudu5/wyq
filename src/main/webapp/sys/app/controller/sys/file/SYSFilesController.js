Ext.define('EJ.controller.sys.file.SYSFilesController', {
	extend : 'Ext.app.Controller',
	init : function() {
		this.control({
					'filesManager' : {// 打开fileManager窗口时load
						afterrender : function(b) {
							// this.getStore('').load();
						}
					},
					'filesManager button[action=open_uploadWin]' : {
						click : function(b) {
							this.getView('sys.files.UploadWindow').create()
									.show();
						}
					},
					'uploadWin button[action=add]' : {
						click : this.doUpload
					},
					'filesManager button[action=download]' : {
						click : function(b) {
							var s = this.filesListSelection(b);
							if (s && s.length > 0) {
								if (s.length > 1) {
									Ext.Msg.show({
												title : '操作错误',
												msg : '暂时系统只支持单文件下!',
												icon : Ext.Msg.ERROR,
												buttons : Ext.Msg.OK
											});
									return;
								} else {
									this.download(b, s[0].get('id'));
								}
							} else {
								Ext.Msg.show({
											title : '操作错误',
											msg : '您没有选择件!',
											icon : Ext.Msg.ERROR,
											buttons : Ext.Msg.OK
										});
								return;
							}
						}
					},
					'filesManager button[action=delete]' : {
						click : function(b) {
							Ext.Msg.confirm('提示', '确定要删除所选内容?', function(v) {
										if (v == 'yes') {
											this.deleteFile(b);
										}
									}, this);
						}
					},
					"filesManager button[action=ok]" : {
						click : function(b) {
							var postfix = Ext.ComponentQuery
									.query("filesManager")[0]
									.down("combo[name=postfix]").getValue();
							var type = Ext.ComponentQuery.query("filesManager")[0]
									.down("combo[name=type]").getValue();
							var shzt = Ext.ComponentQuery.query("filesManager")[0]
									.down("combo[name=shzt]").getValue();
							var grid = b.up("filesManager").down("fileList");
							var c = grid.columns;
							var postfixs = null;
							if (postfix == "PNG" || postfix == 'GIF'
									|| postfix == 'JPG' || postfix == 'BMP') {
								c[1].show();
							} else if (postfix == 'IMG') {
								postfixs = ["png", "gif", "jpg", "bmp"];
								postfix = null;
								c[1].show();
							} else {
								c[1].hide();
							}

							var beginTime = Ext.ComponentQuery
									.query("filesManager")[0]
									.down("datefield[name=beginTime]")
									.getValue();
							var endTime = Ext.ComponentQuery
									.query("filesManager")[0]
									.down("datefield[name=endTime]").getValue();
							if (beginTime && endTime) {
								if (beginTime.getTime() > endTime.getTime()) {
									Ext.Msg.alert("错误", "开始时间不能晚于结束时间。");
									return;
								}
							};

							var store = grid.getStore();
							store.proxy.url = "attached/findbypage";
							store.proxy.extraParams = {
								postfix : postfix,
								postfixs : postfixs,
								type : type,
								shzt : shzt,
								beginTime : beginTime,
								endTime : endTime
							};
							store.load();
						}
					},
					"filesManager button[action=reset]" : { // 重置
						click : function(b) {
							b.up("filesManager").down("combo[name=postfix]")
									.reset();
							b.up("filesManager").down("combo[name=type]")
									.reset();
							b.up("filesManager")
									.down("datefield[name=beginTime]").reset();
							b.up("filesManager")
									.down("datefield[name=endTime]").reset();
							b.up("filesManager").down("combo[name=shzt]")
									.reset();
						}
					},
					'filesManager button[action=imgAction]' : {
						click : function(b) {
							var grid = b.up("filesManager").down("fileList");
							var c = grid.columns;
							c[1].show();
							var store = grid.getStore();
							store.proxy.url = "attached/findbypage";
							var postfixs = ["png", "gif", "jpg", "bmp"];
							store.proxy.extraParams = {
								postfixs : postfixs
							};
							store.load();
						}
					},
					'filesManager button[action=lookAll]' : {
						click : function(b) {
							var grid = b.up("filesManager").down("fileList");
							var c = grid.columns;
							c[1].hide();
							var store = grid.getStore();
							store.proxy.url = "attached/findbypage";
							store.proxy.extraParams = null;
							store.loadPage(1);
						}
					},
					'filesManager menuitem[action=shzt-1]' : {
						click : function(b) {
							this.shzt(b, "1");
						}
					},
					'filesManager menuitem[action=shzt-0]' : {
						click : function(b) {
							this.shzt(b, "0");
						}
					},
					'filesManager menuitem[action=shzt-2]' : {
						click : function(b) {
							this.shzt(b, "2");
						}
					}

				});
	},
	doUpload : function(b) {
		var form = b.up('form').getForm();
		if (form.isValid()) {
			var name = form.findField('attached').getValue();
			form.submit({
						url : 'file/upload',
						waitMsg : 'Uploading your file...',
						success : function(fp, o) {// o.result.file
							b.up('window').close();
//							Ext.Msg.alert('Success',
//									'Your file has been uploaded.');
							Ext.getCmp('filesList').store.reload();
						},
						failure : function(fp, o) {
							Ext.Msg.alert('Failure', o.result.mess);
						}
					});
		}
	},
	download : function(b, id) {
		Ext.Msg.wait('正在下载...');
		window.location.href = 'file/download?id=' + id;
		Ext.Msg.hide();
	},
	deleteFile : function(b) {
		var s = this.filesListSelection(b);
		var ids = [];
		for (var i = 0; i < s.length; i++) {
			ids.push(s[i].get('id'));
		}
		Ext.Ajax.request({
					url : 'file/delete',
					scope : this,
					method : 'POST',
					params : {
						ids : ids
					},
					success : function() {
						Ext.getCmp('filesList').store.reload();
					},
					failure : function(fp, o) {
						Ext.Msg.alert('Failure', o.result.mess);
					}
				});
	},
	shzt : function(b, shzt) {
		var s = this.filesListSelection(b);
		if (s && s.length > 0) {
			var ids = [];
			for (var i = 0; i < s.length; i++) {
				ids.push(s[i].get('id'));
			}
			Ext.Ajax.request({
						url : 'attached/shzt',
						scope : this,
						method : 'POST',
						params : {
							ids : ids,
							shzt : shzt
						},
						success : function() {
							Ext.getCmp('filesList').store.reload();
						},
						failure : function() {
							Ext.Msg.alert('提示', '操作失败！');
						}
					});
		} else {
			Ext.Msg.show({
						title : '操作错误',
						msg : '您没有选择件!',
						icon : Ext.Msg.ERROR,
						buttons : Ext.Msg.OK
					});
			return;
		}
	},
	filesListSelection : function(b) {
		var grid = b.up('filesManager').down('grid');
		return grid.getSelectionModel().getSelection();
	}
});