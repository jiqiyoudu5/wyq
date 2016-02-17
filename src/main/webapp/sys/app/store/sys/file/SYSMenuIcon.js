Ext.define('EJ.store.sys.file.SYSMenuIcon', {
			extend : 'Ext.data.Store',
			model : 'EJ.model.sys.file.SYSAttachedModel',
			proxy : {
				type : 'rest',
				url : 'attached/findbylist',
				extraParams : {
					type : '402883e04ecce66f014eccfad7a30000'
				},
				reader : {
					type : 'json'
				}
			}
		});