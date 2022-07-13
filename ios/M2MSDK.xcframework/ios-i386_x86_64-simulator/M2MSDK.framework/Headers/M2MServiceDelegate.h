//
//  M2MServiceDelegate.h
//  M2MSDK
//
//  Created by Z on 10/1/15.
//  Copyright © 2015 Inmarket llc. All rights reserved.
//
#import "M2MConfig.h"
#import "M2MEnums.h"
/**
 * The M2M SDK's delegate. Implement this protocol to receive SDK updates
 */
@protocol M2MServiceDelegate <NSObject>

@optional


/**
 * This method is called when M2M makes a decision around the user. 
 * M2M returns some additional data around that decision for you
 * @param dict the dictionary containing some of M2M's decision data
 */
-(void)onM2mDecisionWithData:(NSDictionary*)dict;

/***
 * Indicates the start of M2MService
 **/
-(void)onStartM2MService;


/**
 * Indicates stop of the Service
 */
-(void)onM2MServiceStopped;

/**
 * This method is called when M2M is Showing an Engagement
 */
-(void)didShowEngagement;


/**
 * This method is called when M2M engagement is dismissed
 */
-(void)didDismissEngagement;

/**
 * Called when M2M has an engagement ready for viewing.
 * If waitForReady is set on M2MBeaconMonitor,
 * then the app must call [M2MBeaconMonitor readyForEngagement]
 * after this method is called, in order to receive the interstitial.
 *
 * This method is called in the background.
 */
-(void)didReceiveEngagement;

/**
 * This method is called when M2M runs into an error. This method replaces the  -(void)onM2mServiceConfig:(M2MConfig*)config  withCode:(int)code  andMessage:(NSString*)message;
 * @param code the ineteger code for error
 * @param message the message the SDK returns along with the error code
 * @param type inform the user that the error happened with what type of request
 */
-(void)onErrorWithCode:(M2M_ERROR_CODES)code andMessage:(NSString*)message forRequest:(M2M_REQUEST_TYPE)type;


/*
 * This method indicates that the webView has run into error
 */
-(void)onWebViewIntegrityError;


/**
 * This method is called when No Engagement is available on Checkin
 **/
-(void)engagementNotAvailable;

/**
 * This method is called when M2M there are Checkin Opportunities available
 * @param opps Json string of available Opportunities
 *
 * @example Sample Json for List of checkin Opps:<pre><code class=" objectivec">{<br/> "locations" = [{<br/>                "addr" = "1350 Abbot Kinney Boulevard",<br/>                "chain_name" = "InMarket Media LLC",<br/>                 "lat" = "33.990707",<br/>                 "lon" = "-118.466286",<br/>                 "opportunities" = 1,<br/>                 "place_id" = 999200029766<br/>             },<br/>              {<br/>                  "addr" = "1401 Abbot Kinney Blvd",<br/>                  "chain_name" = "Abbots Habit CS ONLY",<br/>                  "lat" = "33.990677",<br/>                  "lon" = "-118.465790",<br/>                  "opportunities" = 1,<br/>                  "place_id" = 999200510017<br/>               },<br/>               {<br/>                   "addr" = "604 Lincoln Blvd",<br/>                   "chain_icon" = "http://dqyk0i3x3xz4k.cloudfront.net/img/locations/2180.png",<br/>                   "chain_name" = "Smart & Final",<br/>                   "lat" = "34.000622",<br/>                   "lon" = "-118.464249",<br/>                   "opportunities" = 1,<br/>                   "place_id" = 41973727<br/>              }...]<br/>          }<br/> </code></pre>
 */
-(void)didGetAvailableOpps:(NSDictionary*)opps;

/**
 * This method provides info on the  detected beacons, when publisher owned beacons are detected by the client, and
 * whether or not the detection resulted in a push message
 * @param detection NSDictionary of beacons detected.
 *
 * @example Sample NSDictionary for beacons detected:<pre><code class="objectivec">{<br/>"push_sent"= 1,<br/>"data"= [{<br/>"addr"= "1352 Abbott Kinney Blvd",<br/>"city"= "Venice",<br/>"state"= "CA",<br/>"ibeacon_id"= "1350",<br/>"partner_id": "12345"<br/>}]<br/>}</code></pre>
 */
-(void)didReceiveDetection:(NSDictionary*)detection;


/**
 * This callback Method provides an Array of Locations with products available For Scanning
 * @param ops Array of scannable Opportunities
 *
 * @example Sample Array for Scannable Opportunities:<pre><code class="objectivec">[<br/> {<br/> "id": 999200029766,<br/> "name": "InMarket Media LLC",<br/> "phone": "",<br/> "address": "1350 Abbot Kinney Boulevard, Los Angeles",<br/> "zip": "90291",<br/> "city": "Los Angeles",<br/> "logo": "https://d1iyzzjikyp69h.cloudfront.net/400/xdpi/s/s5812.png",<br/> "lat": 33.990707,<br/> "lon": -118.466286<br/> },<br/>{<br/>"id": 999200510017,<br/>"name": "Abbots Habit CS ONLY",<br/>"phone": "",<br/>"address": "1401 Abbot Kinney Blvd, Venice",<br/>"zip": "90291",<br/>"city": "Venice",<br/>"logo": "https://d1iyzzjikyp69h.cloudfront.net/400/xdpi/s/s5812.png",<br/>"lat": 33.990677,<br/>"lon": -118.465791<br/>}...<br/>]<br/> </code></pre>
 */
-(void)didGetScanOps:(NSMutableArray*)ops;


/**
 * This callback Method provides an Array of Products for Scanning
 * @param products Array of scannable Oppurtunities
 *
 * @example Sample array of Products<pre><code class="objectivec">[<br/>{<br/>"description" = " ",<br/>"image" = "https://d1iyzzjikyp69h.cloudfront.net/400/xdpi/pi/blue_machine_1532362722703.png",<br/>"name" = "Boosted Blue Machine",<br/>"product_id" = 11758,<br/>"upc" =     [<br/>"^098987654",<br/>"^082592727"<br/>],<br/>},<br/> {<br/>"description"= " ",<br/>"image" = "https://d1iyzzjikyp69h.cloudfront.net/400/xdpi/pi/blue_machine_1532362722703.png",<br/>"name" = "Boosted Blue Machine",<br/>"product_id" = 11758,<br/>"upc" =     [<br/>"^098987654",<br/>"^082592727"<br/>],<br/>},<br/>{<br/>"description" = " ",<br/>"image" = "https://d1iyzzjikyp69h.cloudfront.net/400/xdpi/pi/blue_machine_1532362722703.png",<br/>"name" = "Boosted Blue Machine",<br/>"product_id" = 11758,<br/>"upc" =     [<br/>"^098987654",<br/>"^082592727"<br/>],<br/>},<br/> {<br/>"description" = " ",<br/>"image" = "https://d1iyzzjikyp69h.cloudfront.net/400/xdpi/pi/blue_machine_1532362722703.png",<br/>"name" = "Boosted Blue Machine",<br/>"product_id" = 11758,<br/>"upc"" =     [<br/>"^098987654",<br/>"^082592727"<br/>],<br/>}...<br/>]<br/> </code></pre>
 */
-(void)didGetProducts:(NSMutableArray*)products;

@end
