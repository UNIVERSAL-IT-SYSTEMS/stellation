(function()
{
    "use strict";

    S.Classes.SCargoship =
    {	
    	showSummaryDetail: function ()
    	{
    		var e = $("<span/>");
    		S.ExpandTemplate(this, e, "cargoship.cargo");
    		return e;
    	},
	
    	createDetails: function (element)
    	{
    		S.Classes.SShip.createDetails.call(this, element);
    		
    		var object = this;
    		
    		var adjust_loading = function(m, a, o)
    		{
    			$("#cargoshipmetal").val(parseInt(m));
    			$("#cargoshipantimatter").val(parseInt(a));
    			$("#cargoshiporganics").val(parseInt(o));
    		};
    		
    		var adjust_to_cargo = function(f)
    		{
    			var m = object.CargoM;
    			var a = object.CargoA;
    			var o = object.CargoO;
    			adjust_loading(m*f, a*f, o*f);
    		};
    		
    		var adjust_to_available = function(f)
    		{
				var star = object.Location.Location;
    			var m = star.ResourcesM;
    			var a = star.ResourcesA;
    			var o = star.ResourcesO;
    			adjust_loading(m*f, a*f, o*f);
    		};
    		
    		var e = $("<tbody/>");
    		$(element).append(e);
        	S.TemplatedMonitor(object, e, "cargoship.details",
        		{
        			_changed: function (object, element)
        			{
        				var star = object.Location.Location;
        				var e = $(element).find(".available");
        				
        				S.TemplatedMonitor(star, e, "cargoship.resources");
        			},
        			
        			cargo_0: function() { adjust_to_cargo(0.00); },        			
        			cargo_25: function() { adjust_to_cargo(0.25); },        			
        			cargo_50: function() { adjust_to_cargo(0.50); },        			
        			cargo_75: function() { adjust_to_cargo(0.75); },        			
        			cargo_100: function() { adjust_to_cargo(1.00); },        			

        			available_0: function() { adjust_to_available(0.00); },        			
        			available_25: function() { adjust_to_available(0.25); },        			
        			available_50: function() { adjust_to_available(0.50); },        			
        			available_75: function() { adjust_to_available(0.75); },        			
        			available_100: function() { adjust_to_available(1.00); },        			
        		}
        	);
    
    	}
    };
})();