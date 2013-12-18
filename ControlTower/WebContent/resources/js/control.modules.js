// Airport module
var Airport = function(){
	var ERROR="ERROR";
	return{
		create:function(nameI, numberOfLandingsI){
			$.ajax({
				  type: "POST",
				  dataType: "json",
				  url: "rest/airport/create",
				  data: { name: nameI, numberOfLandingsFields: numberOfLandingsI },
				  success:function(data){
					  if(data.status===ERROR){
						  // and other error handling action
						  alert(data.message);
					  }
					  else{
						  //alert(data.message);
					  }
				  }
			});
		},
		list:function(){
			var result={};
			$.ajax({
				  type: "GET",
				  dataType: "json",
				  url: "rest/airport/list",
				  async:false,
				  success:function(data){
					  if(data.status===ERROR){
					  }
					  else{
						  result = data.responseObject;
					  }
				  }
			});
			return result;
		},
		setCurrent:function(name){
			var result={};
			$.ajax({
				  type: "POST",
				  dataType: "json",
				  url: "rest/airport/setcurrent",
				  async:false,
				  data:{name:name},
				  success:function(data){
					  if(data.status===ERROR){
					  }
					  else{
						  result = data.responseObject;
					  }
				  }
			});
			return result;
		}
	};
}();


//ControlTower module
var ControlTower = function(){
	var ERROR="ERROR";
	return{
		add:function(){
			var result={};
			$.ajax({
				  type: "POST",
				  dataType: "json",
				  url: "rest/control/aircraft/addtoradar",
				  data: {type:'plane'},
				  success:function(data){
					  if(data.status===ERROR){
					  }
					  else{
						  result = data.responseObject;
						  addPlane(result);
					  }
				  }
			});
			return result;
		},
		park:function(){
			var result={};
			$.ajax({
				  type: "POST",
				  dataType: "json",
				  url: "rest/control/landingfield/clean",
				  success:function(data){
					  if(data.status===ERROR){
						  alert(data.message);
					  }
					  else{
						  result = data.responseObject;
						  parkPlane(result); 
					  }
				  }
			});
			return result;
		},
		land:function(aircId){
			var result={};
			$.ajax({
				  type: "POST",
				  dataType: "json",
				  url: "rest/control/aircraft/land",
				  data:{aircraftid:aircId},
				  success:function(data){
					  if(data.status===ERROR){
						  alert(data.message);
					  }
					  else{
						  result = data.responseObject;
						  landPlane(result);
					  }
				  }
			});
			return result;
		},
		prepareForTakeOff:function(aircId){
			var result={};
			$.ajax({
				  type: "POST",
				  dataType: "json",
				  url: "rest/control/aircraft/takeoff/prepare",
				  data:{aircraftid:aircId},
				  success:function(data){
					  if(data.status===ERROR){
						  alert(data.message);
					  }
					  else{
						  result = data.responseObject;
						  readyForTakeOff(result);
					  }
				  }
			});
			return result;
		},
		takeOff:function(){
			var result={};
			$.ajax({
				  type: "POST",
				  dataType: "json",
				  url: "rest/control/aircraft/takeoff/do",
				  success:function(data){
					  if(data.status===ERROR){
						  alert(data.message);
					  }
					  else{
						  result = data.responseObject;
						  takeOff(result);
					  }
				  }
			});
			return result;
		},
		remove:function(aircId){
			var result={};
			$.ajax({
				  type: "POST",
				  dataType: "json",
				  url: "rest/control/aircraft/removefromradar",
				  data:{aircraftid:aircId},
				  success:function(data){
					  if(data.status===ERROR){
						  alert(data.message);
					  }
					  else{
						  result = data.responseObject;
						  removePlane(aircId);
					  }
				  }
			});
			return result;
		}
	};
}();