Ext.define('EJ.model.sys.file.SYSAttachedModel', {
			extend : 'Ext.data.Model',
			fields : ['id', 'name', {
						name : 'size',
						convert : fileSizeTransform
					}, 'uploadUser', {
						name : 'uploadTime',
						convert : timeConvertor
					}, 'url', 'relateId', 'dataFrom', 'type', 'typeText',
					'remark', 'postfix', 'shzt'],
			idProperty : 'id',
			proxy : {
				type : 'rest',
				actionMethods : {
					create : 'POST',
					read : 'GET',
					update : 'PUT',
					destroy : 'DELETE'
				},
				api : {
					create : 'attached',
					read : 'attached',
					update : 'attached',
					destroy : 'attached'
				}
			}
		});
