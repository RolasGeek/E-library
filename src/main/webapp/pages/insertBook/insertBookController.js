var app = angular.module("myApp")

app.controller("insertBookController", ['$scope', '$rootScope', '$cookieStore', 'BookService' , '$location', function($scope, $rootScope, $cookieStore, BookService ,$location) {
	 $scope.preview = false;
	 $scope.$watch('book.file', function(newfile, oldfile) {
	      if(angular.equals(newfile, oldfile) ){
	        return;
	      }
	      if(newfile.type != 'image/png') {
	    	  $scope.preview = false;
	    	  $scope.error = true;
	    	  return;
	      } else {
	    	  $scope.error = false;
	    	  $scope.preview = true;
	      }
	 
	  });
	
	
	$scope.insert = function(book) {
		console.log(book);
		var formdata = new FormData();
		formdata.append('image', book.file);
		formdata.append('file1', book.file1);
		var loginData =  {author: book.data.author, description: book.data.description, name : book.data.name}
		var json_test = JSON.stringify(loginData);
		formdata.append('book', json_test);
		console.log(formdata);
		BookService.insert(formdata).success(function(data) {
			
			$scope.render=true;
			$scope.alertclass="alert alert-success";
			$scope.alertmessage="Book has been inserted";
		})
	}
	
	    
}]);