Ext.define('EJ.store.sys.file.SYSFileStore', {
			extend : 'Ext.data.Store',
			model : 'EJ.model.sys.file.SYSAttachedModel',
			pageSize : 50,
			proxy : {
				type : 'rest',
				url : 'attached/findbypage',
				reader : {
					type : 'json',
					root : 'items',
					totalProperty : 'totalCount'
				}
			}
		});