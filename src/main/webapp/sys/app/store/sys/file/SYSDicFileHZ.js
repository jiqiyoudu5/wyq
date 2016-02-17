Ext.define('EJ.store.sys.file.SYSDicFileHZ', {
			extend : 'Ext.data.Store',
			model : 'EJ.model.sys.dictionary.SYSDicModel',
			proxy : {
				type : 'rest',
				url : 'dictionary/type',
				extraParams : {
					type : 'fileHZ',
					parentcode : 'wyq8d8i8c8t8y8p8e'
				},
				autoDestroy : true,
				reader : {
					type : 'json'
				},
				writer : {
					type : 'json'
				}
			},
			constructor : function() {
				this.callParent(arguments);
			}
		});