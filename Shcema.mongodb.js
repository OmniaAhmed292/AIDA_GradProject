//CREATE AND USE DATABASE AIDA
use (AIDA)

// Collection for users with card embedded
db.createCollection("users", {
   validator: {
     $jsonSchema: {
       bsonType: "object",
       required: ["Fname", "Lname", "email", "Hashed_Password", "User_type"],
       properties: {
         user_id: { bsonType: "int" },
         Fname: { bsonType: "string" },
         Lname: { bsonType: "string" },
         email: {
           bsonType: "string",
           pattern: "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", // Email format regex
         },
         Hashed_Password: { bsonType: "string" },
         User_type: { bsonType: "string", enum: ["customer", "vendor"] },
         phone_number: {
           bsonType: "string",
           pattern: ".{6,}", // Minimum length for phone number 
         },
         address_city: { bsonType: "string" },
         address_street: { bsonType: "string" },
         address_apartment_no: { bsonType: "string" },
         address_Building_no: { bsonType: "string" },
         image_file_path: { bsonType: "string" },
         image_file_name: { bsonType: "string" },
         balance: { bsonType: "decimal" },
         //Cards saved by the user
         cards: {
            bsonType: "array",
            items: {
              bsonType: "object",
              properties: {
                card_number: { bsonType: "string", maxLength: 20 },
                year: { bsonType: "int" },
                month: { bsonType: "int", minimum: 1, maximum: 12 },
              },
            },
          },

       },
     },
   },
 });
 
 // Collection for customers wit subscriped products embedded
 db.createCollection("customers", {
   validator: {
     $jsonSchema: {
       bsonType: "object",
       required: ["birthdate", "Gender", "points"],
       properties: {
         customer_id: { bsonType: "int" }, // Inherits user_id from users
         birthdate: { bsonType: "date" },
         Gender: { bsonType: "string", enum: ["Male", "Female", "Other"] },
         Last_Modified_Time: { bsonType: "date", default: ISODate() }, //ISODate used for timestamps
         allow_Deactivated: { bsonType: "bool" },
         allow_email_subscribed: { bsonType: "bool" },
         allow_Email_Cart_Recovery: { bsonType: "bool" },
         allow_Collect_information: { bsonType: "bool" },
         points: { bsonType: "int", default: 0 },
         subscribed_products: {
            bsonType: "array",
            items: {
              bsonType: "object",
              properties: {
                product_id: { bsonType: "int" }, // References products collection (FK)
                subscription_start_date: { bsonType: "date" }, // Optional for tracking start time
                subscription_end_date: { bsonType: "date" }, // Optional for tracking end time (if applicable)
              },
            },
          },
        },
     },
   },
 });
 
 
 // Collection for vendors
 db.createCollection("vendors", {
   validator: {
     $jsonSchema: {
       bsonType: "object",
       required: ["About_us_info", "business_type", "business_name", "exp_day", "exo_month", "allow_Late_emails", "allow_new_emails"],
       properties: {
         vendor_id: { bsonType: "int" }, // Inherits user_id from users
         About_us_info: { bsonType: "string" },
         business_type: { bsonType: "string", maxLength: 50 },
         business_name: { bsonType: "string", maxLength: 100 },
         exp_day: { bsonType: "date" },
         exo_month: { bsonType: "string", enum: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"] },
         allow_Late_emails: { bsonType: "bool" },
         allow_new_emails: { bsonType: "bool" },
         Application_files_path: { bsonType: "string", maxLength: 255 },
         shelves: {
            bsonType: "array",
            properties: {
              shelf_name: { bsonType: "string", maxLength: 50 },
              shelf_id: { bsonType: "int", autoincrement:true }, // Unique identifier for the shelf
            },
       },
       // Embedded reviews information
       reviews: {
         bsonType: "array",
         items: {
           bsonType: "object",
           properties: {
             customer_id: { bsonType: "int" }, // Can reference customers collection (optional)
             body: { bsonType: "string" },
             rate: { bsonType: "int", minimum: 1, maximum: 5 },
             date: { bsonType: "date" },
           },
         },
       },
     },
   },
   },
 });
 
 //Dummy admin (doesn't inherit from users)
 db.createCollection("Dummy_Admin", {
   validator: {
     $jsonSchema: {
       bsonType: "object",
       required: [
         "service_fees",
         "points_to_discount_ratio",
         "Shipment_fees",
         "Banner_price",
         "bank_acount",
       ],
       properties: {
         Dummy_Admin_id: { bsonType: "int" }, // doesn't inherit user_id from users
         service_fees: { bsonType: "decimal", maximum: 100.0, minimum: 0.0 }, // Percentage (max 100.0)
         points_to_discount_ratio: { bsonType: "decimal", maximum: 100.0, minimum: 0.0 }, // Percentage (max 100.0)
         Shipment_fees: { bsonType: "decimal" },
         Banner_price: { bsonType: "decimal" }, //maximum Price for the banner of the budget concious customers
         bank_acount: { bsonType: "string", maxLength: 50 }, //Represents the account at which system money will be transferred
       },
     },
   },
 });



 //Discount codes collection
 db.createCollection("discounts", {
   validator: {
     $jsonSchema: {
       bsonType: "object",
       required: ["percentage", "discount_type", "EndDate", "Code"],
       properties: {
         discount_id: { bsonType: "int", autoincrement: true },
         percentage: { bsonType: "decimal", minimum: 0.0, maximum: 100.0 },
         discount_type: { bsonType: "string", enum: ["time_limited", "number_limited"] },
         EndDate: { bsonType: "date" },
         max_purchase: { bsonType: "decimal", minimum: 0.0 }, // Can be null
         current_purchase: { bsonType: "decimal", minimum: 0.0 },
         Code: { bsonType: "string", maxLength: 20, unique: true },
       },
     },
   },
 });

 //Tags collection
 db.createCollection("tags", {
   validator: {
     $jsonSchema: {
       bsonType: "object",
       required: ["tag_name"],
       properties: {
         tag_id: { bsonType: "int", autoincrement: true }, // Use counters instead
         tag_name: { bsonType: "string", maxLength: 50, unique: true },
       },
     },
   },
 });

 //Products collection with embedded specifications and images
 db.createCollection("products", {
   validator: {
     $jsonSchema: {
       bsonType: "object",
       required: [
         "product_name",
         "quantity",
         "description",
         "is_used",
         "time_since_restocking",
         "price",
         "subscription_date",
         "taxes",
         "is_shown",
         "category_name",
         "purchases_no",
         "shelf_id",
         "vendor_id",
       ],
       properties: {
         product_id: { bsonType: "int", autoincrement: true }, // Use counters instead
         product_name: { bsonType: "string", maxLength: 50 },
         quantity: { bsonType: "int", minimum: 0 },
         discount: { bsonType: "decimal", minimum: 0.0 }, // Moved from separate table
         description: { bsonType: "string", maxLength: 100 },
         is_used: { bsonType: "bool" },
         deletion_date: { bsonType: "date" }, // Can be embedded if needed
         time_since_restocking: { bsonType: "date" },
         price: { bsonType: "decimal", minimum: 0.0 },
         subscription_date: { bsonType: "date" },
         taxes: { bsonType: "decimal", minimum: 0.0 },
         is_shown: { bsonType: "bool" },
         category_name: { bsonType: "string", maxLength: 50 },
         purchases_no: { bsonType: "int", minimum: 0.0 },
         discount_id: { bsonType: "int" }, // References discount document (FK) (optional)
         is_in_event: { bsonType: "bool" }, // Embedded event information if needed
         // Embedded specifications
         specifications: {
           bsonType: "array",
           items: {
             bsonType: "object",
             properties: {
               attribute_name: { bsonType: "string", maxLength: 50 },
               attribute_value: { bsonType: "string", maxLength: 100 },
             },
           },
         },
         // Reference to tags using a separate collection (not embedded)
         tags: {
           bsonType: "array",
           items: {
             bsonType: "object",
             properties: {
               tag_id: { bsonType: "int" }, // Reference to tags collection
             },
           },
         },
         // Embedded images
         images: {
           bsonType: "array",
           items: {
             bsonType: "object",
             properties: {
               image_name: { bsonType: "string", maxLength: 50 },
               image_description: { bsonType: "string", maxLength: 255 },
               file_path: { bsonType: "string" },
             },
           },
         },
         vendor_id: { bsonType: "int" }, // References vendor document (FK)
         // Reference to specific shelf within vendor document (FK)
         shelf_id: { bsonType: "int" }, // References shelf object inside vendor.shelves
        
       },
     },
   },
 });
 
 //Reviews collection (with the original products schema without reviews)
 db.createCollection("product_reviews", {
   validator: {
     $jsonSchema: {
       bsonType: "object",
       required: ["customer_id", "product_id", "body", "rate", "date"],
       properties: {
         review_id: { bsonType: "int", autoincrement: true }, // Use counters instead
         customer_id: { bsonType: "int" }, // References customers collection (FK)
         product_id: { bsonType: "int" }, // References products collection (FK)
         body: { bsonType: "string" },
         rate: { bsonType: "int", minimum: 1, maximum: 5 },
         date: { bsonType: "date" }, 
      },
   },
},
 }); 

//Order with order-items embedded
db.createCollection("orders", {
   validator: {
     $jsonSchema: {
       bsonType: "object",
       required: [
         "customer_id",
         "shipment_price",
         "order_date",
         "card_id",
         "address_city",
         "address_apartment_no",
         "address_building_no",
         "address_street"
       ],
       properties: {
         order_id: { bsonType: "int", autoincrement: true }, // Use counters instead
         customer_id: { bsonType: "int" }, // References customers collection (FK)
         percentage_discount: { bsonType: "decimal", minimum: 0.0, maximum: 1.0 }, // Assuming percentage is between 0 and 1
         shipment_price: { bsonType: "decimal", minimum: 0.0 },
         order_date: { bsonType: "date" },
         card_id: { bsonType: "int" }, // References cards collection (FK) or not
         address_city: { bsonType: "string", maxLength: 50 },
         address_apartment_no: { bsonType: "string", maxLength: 10 },
         address_building_no: { bsonType: "string", maxLength: 10 },
         address_street: { bsonType: "string", maxLength: 100 },
         order_items: {
           bsonType: "array",
           items: {
             bsonType: "object",
             required: ["product_id", "quantity", "Status", "Taxes", "Product_price", "Discount_price"],
             properties: {
               product_id: { bsonType: "int" }, // References products collection (FK)
               quantity: { bsonType: "int", minimum: 1 },
               Status: { bsonType: "string", enum: ["pending", "shipped", "delivered"] },
               Taxes: { bsonType: "decimal", minimum: 0.0 },
               Product_price: { bsonType: "decimal", minimum: 0.0 },
               Discount_price: { bsonType: "decimal", minimum: 0.0 },
             },
           },
         },
       },
     },
   },
 });
 
//Events tags collection
db.createCollection("event_tags", {
   validator: {
     $jsonSchema: {
       bsonType: "object",
       required: ["name", "start_date", "end_date"],
       properties: {
         tag_id: { bsonType: "int", autoincrement: true }, // Use counters instead
         name: { bsonType: "string", maxLength: 50 },
         start_date: { bsonType: "date" },
         end_date: { bsonType: "date" },
         event_description: { bsonType: "string", maxLength: 255 },
         image_name: { bsonType: "string", maxLength: 255 },
         image_filepath: { bsonType: "string", maxLength: 255 },
       },
     },
   },
 });

//Product_tag relations
 db.createCollection("product_event_tags", {
   validator: {
     $jsonSchema: {
       bsonType: "object",
       required: ["PID", "EID"],
       properties: {
         PID: { bsonType: "int" }, // References products collection (FK)
         EID: { bsonType: "int" }, // References event_tags collection (FK)
         image_name: { bsonType: "string", maxLength: 50 },
         file_path: { bsonType: "string" }, // Optional for storing image path (if needed)
       },
       additionalProperties: false, // Enforce schema validation
     },
   },
   primaryKey: { _id: false, PID: 1, EID: 1 }, // Define compound primary key
 });