//
//  M2MEnums.h
//  M2MSDK
//
//  Created by Sowrirajan Sampath on 10/10/18.
//  Copyright © 2018 inMarket LLC. All rights reserved.
//

#ifndef M2MEnums_h
#define M2MEnums_h

typedef enum {
    M2M_REQUEST_INIT = 0,
    M2M_REQUEST_CHECKIN_PLACES,
    M2M_REQUEST_MANUAL_CHECKIN,
    M2M_REQUEST_DEBUG_REQUEST,
    M2M_SCANSENSE_LOCATION,
    M2M_SCANSENSE_PRODUCT,
    M2M_REQUEST_OTHERS
} M2M_REQUEST_TYPE;

typedef enum{
    MOMENTS_AD = 0,
    INHAND_AD,
    CHECKIN_AD,
} AD_TYPE;

typedef enum {
    M2M_NO_LOCATION_PERMISSION_ERROR_CODE   = -11,
    M2M_NO_PRECISE_LOCATION_PERMISSION_ERROR_CODE   = -12,
    M2M_INVALID_RESPONSE_ERROR_CODE         = -21,
    M2M_NOT_INITIALIZED_ERROR_CODE          = -98,
    M2M_NO_STATUS_ERROR_CODE                = -99,
    M2M_UNKNOWN_ERROR_CODE                  = -100,
    M2M_TIME_OUT_ERROR_CODE                 = -110,
    M2M_PENDING_CHECKIN_OPPS_ERROR_CODE     = -221,
    M2M_PENDING_CHECKIN_ERROR_CODE          = -222,
    M2M_PENDING_SCAN_OPPS_ERROR_CODE        = -223,
    M2M_NO_ADS_AVAILABLE_ERROR_CODE         = -311,
    M2M_ENGAGEMENT_EXPIRED_ERROR_CODE       = -400,
    M2M_E2_LOAD_ERROR_CODE                  = -500,
    M2M_INIT_EXPIRED_ERROR_CODE             = -2002,
    M2M_INVALID_APP_UUID                    = -3002,
    M2M_APP_UUID_REQUIRED                   = -3001,
    
    M2M_SCANSENSE_NO_PUB_USERID             = -5001,
    M2M_SCANSENSE_NO_LOCATION               = -5002,
    M2M_SCANSENSE_NO_PRODUCT                = -5003,
    M2M_SCANSENSE_NO_PRECISE_LOCATION       = -5004,
    
    M2M_SCANSENSE_WRONG_UPC                 = -6001,
    M2M_SCANSENSE_REJECTED                  = -6002
} M2M_ERROR_CODES;


#endif /* M2MEnums_h */
