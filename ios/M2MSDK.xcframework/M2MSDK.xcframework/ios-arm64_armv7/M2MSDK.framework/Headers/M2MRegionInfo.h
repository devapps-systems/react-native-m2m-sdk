//
//  M2MRegionInfo.h
//  M2MSDK
//
//  Created by Sowrirajan Sampath on 12/04/18.
//  Copyright © 2018 inMarket LLC. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CLCircularRegion.h>

@interface M2MRegionInfo : NSObject{
    
    CLCircularRegion        *circularRegion;
    NSString                *locationId;
    
}
@property(nonatomic,readonly )CLCircularRegion        *circularRegion;
@property(nonatomic,readonly )NSString                *locationId;
@property(nonatomic,readonly )NSString                *address;
@property(nonatomic,readonly )NSString                *chainName;


-(id)initWithRegion:(CLCircularRegion*)region andData:(NSDictionary*)data;

@end
