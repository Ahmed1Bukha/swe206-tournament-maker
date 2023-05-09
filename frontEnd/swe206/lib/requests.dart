// ignore_for_file: avoid_print

import 'dart:convert';
import 'dart:io';

import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;

class Requests {
  static var client = http.Client();
  static String url = '127.0.0.1:8080';
  static Map<String, String> header = {"Content-Type": "application/json"};

  static Future getRequest(String endpoint) async {
    try {
      var response = await client.get(
        Uri.http(url, "/$endpoint"),
        headers: header,
      );
      print(response.statusCode);
      var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes));
      return decodedResponse;
    } catch (e) {
      throw HttpException("Get request to /$endpoint failed");
    }
  }

  static Future<Map> postRequest(String endpoint, Map body) async {
    try {
      var response = await client.post(
        Uri.http(url, "/$endpoint"),
        body: jsonEncode(body),
        headers: header,
      );
      var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;
      return decodedResponse;
    } catch (e) {
      throw HttpException("Post request to /$endpoint with body $body failed");
    }
  }

  static Future authUser(String userName, String password) async {
    //TODO make sure that u make the difference between admin and student/
    var authUrl = Uri.parse(
        "https://us-central1-swe206-221.cloudfunctions.net/app/UserSignIn?username=${userName}&password=${password}");
    print(0);
    try {
      print(authUrl);
      var response = await client.get(
        authUrl,
      );
      print(response.headers);

      if (response.statusCode == 200) {
        print("Pog user");
        var decoderes = jsonDecode(utf8.decode(response.bodyBytes));
        print(decoderes);
      } else if (response.statusCode == 403) {
        print("Not pog user");
      }
    } catch (e) {
      print(e);
      throw HttpException("failed");
    }
  }

  // static Future<Map> getTournaments() async {
  //   return getRequest("Tournaments");
  // }

  // Future<Map> putRequest(String endpoint) async {
  //   try {
  //     var response = await client.put(
  //       Uri.http(url, "/$endpoint"),
  //       headers: header,
  //     );
  //     var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;
  //     return decodedResponse;
  //   } catch (e) {
  //     throw HttpException("Get request to /$endpoint failed");
  //   }
  // }

  // Future<Map> deleteRequest(String endpoint) async {
  //   try {
  //     var response = await client.delete(
  //       Uri.http(url, "/$endpoint"),
  //       headers: header,
  //     );
  //     var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;
  //     return decodedResponse;
  //   } catch (e) {
  //     throw HttpException("Get request to /$endpoint failed");
  //   }
  // }

  static void close() => client.close();
}
