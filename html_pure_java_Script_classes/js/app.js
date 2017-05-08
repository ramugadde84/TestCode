function init() {
	spliceTest();
    //spliceTest.call();
    splitTest();
    arrayIterate();
 }

function spliceTest() {
  //splice
   var a=["a","b","c","d"]; 
   //first Parameter-position,second - no of elements
   a.splice(1,3,"e","f","g","h");//(1-"b",0-"dont delete","c","d")

   console.log(a);

}

function splitTest() {
   var a ="test,data,value,sample"; //String
 
   console.log(a); //String

   var arr=a.split(",");//delimeter

   console.log(arr); //array

   //var arr=["test","data","value","sample"];
   for(var i=0;i<arr.length;i++) {
   	 console.log(arr[i]);
   }

   //application scenario;

   var data="ramu,gadde";

   var dataArr=data.split(",");

   var concatData="";

   for(var i=0;i<dataArr.length;i++) {
   	  concatData += dataArr[i].concat("-"); 
   }
   
   //Try With Splice(ramu-gadde-)
   concatData=concatData.substring(0,concatData.length-1);
   console.log(concatData);
   
   document.getElementById("name").innerText=concatData;

}


function arrayIterate() {
  var a= ["test","data","value","sample"];
  for(var i=0;i<a.length;i++) {
  	console.log(a[i]);
  }
} 





   




