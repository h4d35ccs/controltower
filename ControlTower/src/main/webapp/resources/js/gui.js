/* 
 * JavaScript file with Plane Landing System UI Functions
 */
$(document).ready(function(){
    
	/*Loading of backend info on ui*/ 
    //hide add button
	//$('#button_add_airport').hide();
	
	//load carousel
	$('.carousel').carousel({
		interval: 2000
	});
	
	//hides the airport GUI
	$('#airport_container').hide();
	
	// Loading of airports list
    var airportList = Airport.list();

    if(airportList.length>0){
	    // filling select list of airports
	    for(a in airportList){
			  var airport = airportList[a];
		      $('#airport_list_select .dropdown-menu')
		      .append('<li class="airport-select-item" data-id="'+airport.name+'">'+
		    		  '<a href="#">'+
		    		  airport.name+
		    		  '</a></li>');
		}
    }
    else{
    	$('#airport_list_select').hide();
    }
    
    $('.dropdown-toggle').dropdown();
     
    //TODO: FIX-Landing return values
    //Load the first airplane in the arrival line by default
    //ControlTower.add();
    
    /*End Loading of backend info on ui*/
	
    
    /*Buttons Functions*/
    //Add new plane
    $('#button_add').bind('click',function(){
    	ControlTower.add(); //validation to create the plane in the landing arrival line    	
    });
    
    //remove plane from radar
    $('#button_remove').bind('click',function(){
       //validate that the plane can be removed
    	if($("ul#arrivals").children().hasClass('plane')){
    		var id = $("ul#arrivals li.plane").last().attr("id");
    		id= id.split("_").pop();
    		ControlTower.remove(id);
    	}
    });
    
    //land plane
    $('#button_land').bind('click',function(){
    	//validate that the plane can land
    	var land = false;
        if($("ul#arrivals").children().hasClass('plane')){
           $('[id^="track_"]').each(function(){
               if($(this).is(':empty')){
            	   land = true;
            	   ControlTower.land(); //return the plane which can land
                   return false;
               }
           });
           
           if(!land){
               alert('All the Landing Fields are busy.\nPlease wait...'); 
           }
        }
        else{
        	alert("There are no planes in the Radar Arrival Line.");
        }
    });
    
    //park planes
    $('#button_park').bind('click',function(){
       //validate that the plane can be removed
    	var empty = 0;
    	var total = $('.track').length;
    	$('[id^="track_"]').each(function(){
            if(!$(this).is(":empty")){
            	ControlTower.park();
            	return false;
            }
            else{
            	empty++;
            }
    	});

    	if(empty==total){
    		alert("All Landing Fields are empty.\nThere are no planes to park.");
    	}
    });
    
    //ready for take off button
    $('#button_ready').bind('click',function(){
    	//validate an empty track available
    	var takeoff = false; 
    	if($("ul#parking_spots").children().hasClass('plane')){
    		 $('[id^="track_"]').each(function(){
    			 if($(this).is(':empty')){
    				takeoff = true;
    				//get the id of the next plane
    				var id = $("ul#parking_spots li.plane").first().attr("id").replace("plane_","");
    				ControlTower.prepareForTakeOff(id);
                   return false;
                 }
    		 });
    		 
    		 if(!takeoff){
                 alert('All the Landing Fields are busy.\nPlease wait...'); 
             }
    	 }
    	 else{
         	alert("There are no planes in the parking spots.");
         }
    });
    
    //take off button  - Removes the plane from the track
    $('#button_takeoff').bind('click',function(){
    	if($('[id^="takeoff_track_"]').children().hasClass('plane')){
    		ControlTower.takeOff();
    	}
    	else{
    		alert("There are no planes ready to take off.");
    	}
    });
    
    //add listeners to display the selected airports
    displayAirport();
    
    //add airport
//    $('#button_add_airport').bind('click',function(){
//       Airport.create("My New Airport", 2); 
//     });
    
    /*End Buttons Functions*/
    
    /*Dialog buttons*/
    $("#btn_save").bind("click",function(){
    	var valid = $("#create_aircraft_form").valid();
    	if(valid){
    		var airport_name = $("#airport_name").val();
    		var tracks_number = parseInt($("#selected_track_value").html());
    		createNewAirport(airport_name,tracks_number);

    		//load the list if it is empty
    		if(jQuery.isEmptyObject(Airport.list()) == true){
   
    			airportList = Airport.list();

    			// filling select list of airports
    		    for(a in airportList){
    				  var airport = airportList[a];
    			      $('#airport_list_select .dropdown-menu')
    			      .append('<li class="airport-select-item" data-id="'+airport.name+'">'+
    			    		  '<a href="#">'+
    			    		  airport.name+
    			    		  '</a></li>');
    			}
    			//show list    
    		    $('#airport_list_select').show();

    		    //add listeners to display the selected airports
    		    displayAirport();
    		}

    		//close the dialog after adding a new airport
    		$('#airport_creator_container').modal('hide');
    		//empty the dialog
    		$("#airport_name").val("");
    		
    	}
    });
    
    $('[id^="dropdown_track_value_"]').each(function(){
    	$(this).bind("click",function(){
    		$("#selected_track_value").html($(this).html()+'<b class="caret"></b>');
    	});
    });
    /*End Dialog buttons*/
    
    /*Validation*/
    $("#create_aircraft_form").validate({
    	rules: {
    		airport_name: "required"
	    },
	    messages: {
	    	airport_name: "Please specify the name"
	    }
    });
    /*End Validation*/
});