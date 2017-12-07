/**
 * fileModel.js
 */

app.directive('fileModel',['$parse',function($parse){
	return{
		restrict: 'A',
		link: function(scope,elem,attrs){
			
			elem.bind('change',function(){
				$parse(attrs.fileModel)
				.assign(scope,elem[0].files[0])
				scope.$apply()
				
			})
		}
	}
}])