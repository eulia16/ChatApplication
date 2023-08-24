//
//  squegg.c
//  Squegg
//
//  Created by Ethan's Macbook on 7/11/23.
//

#include "m_pd.h"
#include <stdbool.h>

static t_class *squegg_class;

const int BLUETOOTH_VALUE = 10;

typedef struct _squegg{
    
    t_object   x_obj;//important to follow language conventions, this is generally the first member of these structs for PD externals
    
    t_float    my_squegg_value;//this value will start at 0.0, and then will be adjusted when reading off the device
    //I'm unsure still of the value that is given by the sensor from the squegg device(whether its an int or a float) so we will keep it as a float for now to be safe
    t_outlet  *out_a;
        
} t_squegg;

void squegg_resetCount(t_squegg *x){
    x->my_squegg_value = 0;
}

void squeggOnBang(t_squegg *x){
    post("the squegg has been banged");
    //send the bang to the outlet
    outlet_float(x->out_a, x->my_squegg_value);
    
}


void connect_with_squegg_device(){
    
    post("attemtping to connect with the device...");
    //attemp to establish connection
    bool connected = false;
    
    if(connected)
        post("Device connected, transmitting grip strength data");
    
    else
        post("Device connection failed.");
    
    
}

void listen_method(t_squegg *x, t_floatarg f){

    post("attempting to connect to squegg device ");
    //dbus connection potentially to squegg device or straight bluetooth connection,
    //awaiting receiving the device for further development
    
}


void squegg_list_values(t_squegg *x, t_symbol *s, t_int argz, t_atom *argv){
    
    //switch case to allow for control flow depending on what user entered in the list
    switch(argz){
            
        case 2:
            
            //connect with the squegg device using bluetooth, this is where having the device is
            //pretty necessary as it is necessary to establish a connection/grab the squeeeze value from the device
            connect_with_squegg_device();
           
            
            break;
            
        default:
            post("improper squegg inlet usage, 2 arguments required");
            
    }
    
}

void empty_method_for_future_additions(t_squegg *x){
    post("awaiting future implementation...");
}

//this function acts as an initializer for the squegg object/external in pure data
void *squegg_new(t_floatarg t1){
    t_squegg *x = (t_squegg *) pd_new(squegg_class);
    
    
    x->out_a = outlet_new(&x->x_obj, &s_float);//make outlet
    x->my_squegg_value = t1;
    
    
    return (void *)x;
    
}


void squegg_free(t_squegg *x){
    outlet_free(x->out_a);//free the outlet
}

void squegg_setup(void){
    
    squegg_class = class_new(gensym("squegg"), //name to initialize with
                             (t_newmethod) squegg_new, //calls method to initialize
                             (t_method) squegg_free, //function called whenever object is deleted, 0 indicates method not yet created
                             sizeof(t_squegg), // memory of class
                             CLASS_DEFAULT, // appearance of object, since it will be normal(1 input 1 output), we will use class default
                             A_DEFFLOAT, //creation args, we just want 1 default float arg
                             0 // indicates no more arguments
                             );
    
    class_addbang(squegg_class,(t_method) squeggOnBang);
    
    //methods to handle certain list input(tbd)
    
    //method to listen for squegg values(still not sure how squegg works, will be emtpy method mostly)
    class_addlist(squegg_class, (t_method)squegg_list_values);
    
    class_addmethod(squegg_class, (t_method)listen_method, gensym("Listen"), A_DEFFLOAT, 0);
   
    //method for any other potential inlets, will connect to a completely empty method
    class_addmethod(squegg_class, (t_method)empty_method_for_future_additions, gensym("empty"), 0);

    
    
}
