// Code goes here
var app=angular.module("profileInformation",["ngRoute"]);

app.config(function($routeProvider){
  $routeProvider.when("/information/list",{
     templateUrl:"view-list.html",
     controller:"profileInformationListController"
  })
  .when("/information/add",{
  	templateUrl:"add-information.html",
  	controller:"profileInformationAddController"
  })
  .when("/information/edit/:index",{
  	templateUrl:"add-information.html",
  	controller:"profileInformationAddIndexController"
   })
  .otherwise({
  	redirectTo:"/information/list"
  })

});


app.factory("profileInformationService",["$rootScope",function($rootScope){
  var svc={};
  
  var data= [
      {name:"ramu",state:"Telangana",city:"Kodad"},
      {name:"Vasantha",state:"Telangana",city:"Kodad"},
      {name:"Jeevana",state:"Telangana",city:"Kodad"}
   ];

   svc.fetchProfilesList = function() {
   	return data;
   };


   svc.addProfile = function(information) {
     data.push(information);

   };


   svc.updateProfile = function(index,information) {
     data[index] = information; 	
    };

  return svc;
}])

app.controller("profileInformationListController",["$scope","$location","$routeParams"
	,"profileInformationService",
function($scope,$location,$routeParams,profileInformationService){
   $scope.data= profileInformationService.fetchProfilesList();
     
     $scope.addProfile = function() {
     	$location.path("/information/add");
     };

     $scope.editProfile = function(x) {
     	$location.path("/information/edit/"+x);
     };


}]);




app.controller("profileInformationAddController",["$scope","$location","$routeParams"
	,"profileInformationService",function($scope,$location,$routeParams,profileInformationService){
   $scope.save = function() {
     profileInformationService.addProfile({name:$scope.information.name,state:$scope.information.state
     	,city:$scope.information.city});
     $location.path("/information/list");
   };

   $scope.cancel = function() {
    $location.path("/information/list");
   };   
}]);


app.controller("profileInformationAddIndexController",["$scope","$location","$routeParams"
	,"profileInformationService"
	,function($scope,$location,$routeParams,profileInformationService){
   $scope.information = profileInformationService.fetchProfilesList()[parseInt($routeParams.index)];
    
   $scope.save = function() {
     profileInformationService.updateProfile(parseInt($routeParams.index),{name:$scope.information.name,state:$scope.information.state
     	,city:$scope.information.city});
     $location.path("/information/list");
   };

   $scope.cancel = function() {
    $location.path("/information/list");
   };   
}]);

