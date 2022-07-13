//
//  CPError.h
//  CheckPoints
//
//  Created by nithyasriram on 11/17/10.
//  Copyright 2010 Checkpoints. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "M2MServiceDelegate.h"

#define kMaxRetryCount 3



typedef enum {
	M2M_INVALIDJSON = 0,
	M2M_SERVERERROR,
	M2M_CLIENTERROR,
	M2M_NETWORKERROR,
    M2M_TIME_OUT_ERROR,
    M2M_SCAN_LOCAL_ERROR,
    M2M_UNKNOWNERROR
} ERROR_TYPES;

@interface M2M_Error : NSObject {
	
	NSString		    *errorMessage;
	M2M_ERROR_CODES     errorCode;
    ERROR_TYPES         errorType;
    M2M_REQUEST_TYPE    requestType;
}
@property (nonatomic,strong)	NSString                   *errorMessage;
@property (nonatomic,assign)	M2M_ERROR_CODES            errorCode;
@property (nonatomic)           ERROR_TYPES                errorType;
@property (nonatomic)           M2M_REQUEST_TYPE           requestType;


/**
 * Constructor to initialise an M2M_Error Object

 * @param eCode error Code
 * @param eMessage error Message
 * @param type error Type
 * @return M2M_Error Object
 */
-(id)initWithCode:(M2M_ERROR_CODES)eCode message:(NSString*)eMessage andType:(ERROR_TYPES)type;


@end
