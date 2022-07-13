//
//  M2M_RequestLogDelegate.h
//  M2MSDK
//
//  Created by Sowrirajan Sampath on 02/04/18.
//  Copyright © 2018 inMarket LLC. All rights reserved.
//

#ifndef M2M_RequestLogDelegate_h
#define M2M_RequestLogDelegate_h
#import <Foundation/Foundation.h>

@class M2M_BaseRequest;
@class M2M_Error;
@protocol M2M_RequestLogDelegate <NSObject>
@optional

-(void)getBodyAsJson:(NSString*)jsonString ForIdentifer:(NSString*)identifier;
-(void)onBeforeSendRequest: (NSURLRequest*)request ForIdentifier:(NSString*)identifier;
-(void)onBeforeHandleResponse:(NSString*)responseJSON ForIdentifier:(NSString*)identifier;
-(void)onAfterHandleResponse:(NSString*)responseJSON ForIdentifier:(NSString*)identifier;
-(void)onError:(M2M_Error*)error ForIdentifier:(NSString*)identifier;
@end


#endif /* M2M_RequestLogDelegate_h */
