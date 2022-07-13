//
//  M2MConfig.h
//  M2MSDK
//
//  Created by Sowrirajan Sampath on 7/16/14.
//  Copyright (c) 2014 Inmarket llc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface M2MConfig : NSObject


-(id)initWithOptInForPush:(BOOL)optIn andOptInForGeofence:(BOOL)geofenceOptIn andStopped:(BOOL)stopped andWaitForReady:(BOOL)waitForReady;

/**
 * Whether or not the SDK is opted in to geofencing
 * @return the optIn
 */
-(BOOL)isOptedInForGeofencing;

/**
 * Whether or not the SDK is opted in to push
 * @return the optIn
 */
-(BOOL)isOptedInForPush;

/**
 * Whether or not M2M SDK is stopped
 * @return stopped bool
 */
-(BOOL)isStopped;

-(BOOL)waitForReady;

@end
