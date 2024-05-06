//CREATE AND USE DATABASE AIDA
db.users.createIndex({ email: 1 })
use AIDA;
db.createCollection("users", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["Fname", "Lname", "email", "User_type"],
            properties: {
                _id: { bsonType: "objectId" },
                Fname: { bsonType: "string" },
                Lname: { bsonType: "string" },
                email: {
                    bsonType: "string",
                    pattern: "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$" // Email format regex
                },
                Hashed_Password: { bsonType: "string" },
                User_type: { bsonType: "string", enum: ["customer", "vendor"] },
                is_enabled: { bsonType: "bool" },
                is_account_locked: { bsonType: "bool" },

                confirmation_tokens: {
                        bsonType: "object",
                        properties: {
                            token: { bsonType: "string" },
                            creation_date: { bsonType: "date" },
                            expiration_date: { bsonType: "date" },
                            confirmed_date: { bsonType: "date" }
                        }

                }
            }
        }
    }
});

db.createCollection("customers", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["first_name", "last_name", "email", "hashed_password"],
            properties: {
                // User identification and authentication information
                _id: { bsonType: "objectId" },
                first_name: { bsonType: "string", description: "First name of the customer" },
                last_name: { bsonType: "string", description: "Last name of the customer" },
                email: {
                    bsonType: "string",
                    pattern: "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", // Email format regex
                    description: "Email address of the customer"
                },
                hashed_password: { bsonType: "string", description: "Hashed password for the customer's account" },

                // Personal information
                birthdate: { bsonType: "date", description: "Birthdate of the customer" },
                gender: { bsonType: "string", enum: ["Male", "Female", "Other"], description: "Gender of the customer" },

                // Other attributes
                points: { bsonType: "int", description: "Points accumulated by the customer" },

                // Timestamps
                created_at: { bsonType: "date", description: "Timestamp for when the document was created" },
                updated_at: { bsonType: "date", description: "Timestamp for when the document was last updated" },

                // Settings
                settings:{
                    bsonType: "object",
                    properties: {
                        allow_Deactivated: { bsonType: "bool", description: "Flag to allow deactivation" },
                        allow_email_subscribed: { bsonType: "bool", description: "Flag to allow email subscription" },
                        allow_Email_Cart_Recovery: { bsonType: "bool", description: "Flag to allow email cart recovery" },
                        allow_Collect_information: { bsonType: "bool", description: "Flag to allow information collection" }
                    },
                    description: "Settings related to the customer's account"
                },

                // Contact information
                phone_number: {
                    bsonType: "string",
                    pattern: ".{6,}", // Minimum length for phone number
                    description: "Phone number of the customer"
                },
                address:{
                    bsonType: "object",
                    properties:{
                        address_city: { bsonType: "string", description: "City of the customer's address" },
                        address_street: { bsonType: "string", description: "Street of the customer's address" },
                        address_apartment_no: { bsonType: "string", description: "Apartment number of the customer's address" },
                        address_Building_no: { bsonType: "string", description: "Building number of the customer's address" }
                    },
                    description: "Address of the customer"
                },

                // Financial information
                balance: { bsonType: "decimal", description: "Balance of the customer's account" },
                cards: {
                    bsonType: "array",
                    items: {
                        bsonType: "object",
                        properties: {
                            card_number: { bsonType: "string", maxLength: 20, description: "Card number of the customer's card" },
                            year: { bsonType: "int", description: "Expiry year of the customer's card" },
                            month: { bsonType: "int", minimum: 1, maximum: 12, description: "Expiry month of the customer's card" },
                        },
                        description: "Cards saved by the customer"
                    },
                },

                // Image information
                image:{
                    bsonType: "object",
                    properties: {
                        file_path: { bsonType: "string", description: "File path of the customer's image" },
                        file_name: { bsonType: "string", description: "File name of the customer's image" }
                    },
                    description: "Image of the customer"
                },
            },
        },
    },
});

// Collection for vendors with user embedded
db.createCollection("vendors", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["first_name", "last_name", "email", "hashed_password"],
            properties: {
                // Vendor identification and authentication information
                _id: { bsonType: "objectId" },
                first_name: { bsonType: "string", description: "First name of the vendor" },
                last_name: { bsonType: "string", description: "Last name of the vendor" },
                email: {
                    bsonType: "string",
                    pattern: "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", // Email format regex
                    description: "Email address of the vendor"
                },
                hashed_password: { bsonType: "string", description: "Hashed password for the vendor's account" },

                // Vendor business information
                business_info: {
                    bsonType: "object",
                    properties: {
                        About_us_info: { bsonType: "string", description: "Information about the vendor's business" },
                        business_type: { bsonType: "string", maxLength: 50, enum: ["Sole Proprietorship", "Partnership", "LLC", "Corporation"], description: "Type of the vendor's business" },
                        business_name: { bsonType: "string", maxLength: 100, description: "Name of the vendor's business" },
                        application_files_path: { bsonType: "string", maxLength: 255, description: "Path to the vendor's application files" },
                        exp_day: { bsonType: "date", description: "Expiration day of the vendor's business license" },
                        exp_month: { bsonType: "string", enum: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"], description: "Expiration month of the vendor's business license" },
                        is_verified: { bsonType: "bool", description: "Flag to indicate if the vendor is verified" },
                    },
                    description: "Business related information of the vendor"
                },

                // Vendor settings
                settings:{
                    bsonType: "object",
                    properties:{
                        allow_Late_emails: { bsonType: "bool", description: "Flag to allow emails for late orders" },
                        allow_new_emails: { bsonType: "bool", description: "Flag to allow emails for new orders" }
                    },
                    description: "Settings related to the vendor's account"
                },


                // Vendor shelves
                shelves: {
                    bsonType: "array",
                    properties: {
                        shelf_name: { bsonType: "string", maxLength: 50, description: "Name of the shelf" },
                        created_at: { bsonType: "date" },
                        productIds: {
                            bsonType: "array",
                            items: { bsonType: "objectId", description: "Unique identifier for the product" },
                            description: "Array of product IDs in the shelf"
                        },
                    },
                    description: "Shelves owned by the vendor"
                },

                // Vendor contact information
                phone_number: {
                    bsonType: "string",
                    pattern: ".{6,}", // Minimum length for phone number
                    description: "Phone number of the vendor"
                },
                address:{
                    bsonType: "object",
                    properties:{
                        address_city: { bsonType: "string", description: "City of the vendor's address" },
                        address_street: { bsonType: "string", description: "Street of the vendor's address" },
                        address_apartment_no: { bsonType: "string", description: "Apartment number of the vendor's address" },
                        address_Building_no: { bsonType: "string", description: "Building number of the vendor's address" }
                    },
                    description: "Address of the vendor"
                },

                // Vendor financial information
                balance: { bsonType: "decimal", description: "Balance of the vendor's account" },
                cards: {
                    bsonType: "array",
                    items: {
                        bsonType: "object",
                        properties: {
                            card_number: { bsonType: "string", maxLength: 20, description: "Card number of the vendor's card" },
                            year: { bsonType: "int", description: "Expiry year of the vendor's card" },
                            month: { bsonType: "int", minimum: 1, maximum: 12, description: "Expiry month of the vendor's card" },
                        },
                        description: "Cards saved by the vendor"
                    },
                },

                // Vendor image information
                image:{
                    bsonType: "object",
                    properties: {
                        file_path: { bsonType: "string", description: "File path of the vendor's image" },
                        file_name: { bsonType: "string", description: "File name of the vendor's image" }
                    },
                    description: "Image of the vendor"
                },

                // Embedded reviews
                reviews: {
                    bsonType: "array",
                    items: {
                        bsonType: "object",
                        required: ["customerId", "body", "rate", "created_at"],
                        properties: {
                            reviewId: { bsonType: "objectId" },
                            customerId: { bsonType: "objectId" },
                            body: { bsonType: "string" },
                            rate: { bsonType: "int", minimum: 1, maximum: 5 },
                            created_at: { bsonType: "date" },
                            updated_at: { bsonType: "date" },
                        },
                    },
                },

                // Timestamps
                created_at: { bsonType: "date", description: "Timestamp for when the document was created" },
                updated_at: { bsonType: "date", description: "Timestamp for when the document was last updated" },
            },
        },
    },
});

db.createCollection("Dummy_Admin", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: [
                "serviceFees",
                "pointsToDiscountRatio",
                "shipmentFees",
                "bannerPrice",
                "bankAccount",
            ],
            properties: {
                // Admin identification
                _id: { bsonType: "objectId" },

                // Financial settings
                service_fees: { bsonType: "decimal", maximum: 100.0, minimum: 0.0, description: "Service fees percentage (max 100.0)" },
                points_to_discount_ratio: { bsonType: "decimal", maximum: 100.0, minimum: 0.0, description: "Points to discount ratio percentage (max 100.0)" },
                shipment_fees: { bsonType: "decimal", description: "Shipment fees" },
                banner_price: { bsonType: "decimal", description: "Maximum price for the banner for budget conscious customers" },

                // Bank account information
                bank_account: { bsonType: "string", maxLength: 50, description: "Account at which system money will be transferred" },

                // Embedded event tags
                event: {
                    bsonType: "object",
                    required: ["name", "startDate", "endDate"],
                    properties: {
                        name: { bsonType: "string", maxLength: 50, description: "Name of the event" },
                        start_date: { bsonType: "date", description: "Start date of the event" },
                        end_date: { bsonType: "date", description: "End date of the event" },
                        event_description: { bsonType: "string", maxLength: 255, description: "Description of the event" },
                        image_name: { bsonType: "string", maxLength: 255, description: "Name of the event image" },
                        image_filepath: { bsonType: "string", maxLength: 255, description: "File path of the event image" },
                    },
                    description: "Event tags associated with the admin"
                },
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
                _id: { bsonType: "objectId" },
                tag_name: { bsonType: "string", maxLength: 50},
                created_at: { bsonType: "date", description: "Timestamp for when the document was created" },
            },
        },
    },
});


db.createCollection("products", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: [
                "productName",
                "quantity",
                "description",
                "isUsed",
                "timeSinceRestocking",
                "price",
                "subscriptionDate",
                "taxes",
                "isShown",
                "categoryName",
                "purchasesNumber",
                "shelfId",
                "vendorId",
            ],
            properties: {
                // Product identification
                _id: { bsonType: "objectId" },

                // Product details
                productName: { bsonType: "string", maxLength: 50 },
                quantity: { bsonType: "int", minimum: 0 },
                description: { bsonType: "string", maxLength: 100 },
                timeSinceRestocking: { bsonType: "date" },
                price: { bsonType: "decimal", minimum: 0.0 },
                taxes: { bsonType: "decimal", minimum: 0.0 },
                categoryName: { bsonType: "string", maxLength: 50 },

                // Flags
                allowSubscription: { bsonType: "bool" },
                isInEvent: { bsonType: "bool" },
                isShown: { bsonType: "bool" },
                isUsed: { bsonType: "bool" },

                // Timestamps
                created_at: { bsonType: "date", description: "Timestamp for when the document was created" },
                updated_at: { bsonType: "date", description: "Timestamp for when the document was last updated" },
                deleted_at: { bsonType: "date", description: "Timestamp for when the document was deleted" },


                // Product specifications
                specifications: {
                    bsonType: "object",
                    properties: {
                        attributeName: { bsonType: "string", maxLength: 50 },
                        attributeValue: { bsonType: "string", maxLength: 100 },
                    },
                },

                // Product tags
                tags: {
                    bsonType: "array",
                    items: {
                        bsonType: "object",
                        properties: {
                            tagId: { bsonType: "objectId"}, // Reference to tags collection
                            tagName: { bsonType: "string", maxLength: 50 }, // Name of the tag
                        },
                    },
                },

                // Product images
                images: {
                    bsonType: "array",
                    items: {
                        bsonType: "object",
                        properties: {
                            imageName: { bsonType: "string", maxLength: 50 },
                            imageDescription: { bsonType: "string", maxLength: 255 },
                            filePath: { bsonType: "string" },
                        },
                    },
                },

                // Embedded reviews
                reviews: {
                    bsonType: "array",
                    items: {
                        bsonType: "object",
                        required: ["customerId", "body", "rate", "created_at"],
                        properties: {
                            customer_id: { bsonType: "objectId" },
                            body: { bsonType: "string" },
                            rate: { bsonType: "int", minimum: 1, maximum: 5 },
                            created_at: { bsonType: "date" },
                            updated_at: { bsonType: "date" },
                        },
                    },
                },

                // Vendor and shelf information
                vendorId: { bsonType: "objectId" },
                shelfId: { bsonType: "objectId" },

                // Product discount
                discount: {
                    bsonType: "object",
                    properties: {
                        percentage: { bsonType: "decimal", minimum: 0.0, maximum: 100.0 },
                        endDate: { bsonType: "date" },
                        code: { bsonType: "string", maxLength: 20 },
                    }
                }
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
                "address",
                "order_items",
                "card",
                "status",
            ],
            properties: {
                _id: { bsonType: "objectId" },
                customer_id: { bsonType: "objectId" }, // References customers collection (FK)
                percentage_discount: { bsonType: "decimal", minimum: 0.0, maximum: 1.0 }, // Assuming percentage is between 0 and 1
                shipment_price: { bsonType: "decimal", minimum: 0.0 },
                created_at: { bsonType: "date" },

                //Embedded address
                address:{
                    bsonType: "object",
                    properties:{
                        address_city: { bsonType: "string" },
                        address_street: { bsonType: "string" },
                        address_apartment_no: { bsonType: "string" },
                        address_Building_no: { bsonType: "string" }
                    }
                },
                //Embedded card
                card:{
                    bsonType: "object",
                    properties:{
                        card_number: { bsonType: "string" },
                        year: { bsonType: "int" },
                        month: { bsonType: "int" }
                    }},

                order_items: {
                    bsonType: "array",
                    items: {
                        bsonType: "object",
                        required: ["product_id", "quantity", "Status", "Taxes", "Product_price", "Discount_price"],
                        properties: {
                            product_id: { bsonType: "objectId" }, // References products collection (FK)
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
