Ext.define('EJ.store.sys.file.SYSDicFileType', {
			extend : 'Ext.data.Store',
			model : 'EJ.model.sys.dictionary.SYSDicModel',
			proxy : {
				type : 'rest',
				url : 'dictionary/type',
				extraParams : {
					type : 'filetype',
					parentcode : 'wyq8d8i8c8t8y8p8e'
				},
				reader : {
					type : 'json'
				}
			},
			constructor : function() {
				this.callParent(arguments);
			}
		});