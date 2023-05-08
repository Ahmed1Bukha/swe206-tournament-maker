import 'dart:convert';
import 'dart:io';

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
      print("1");

      var decodedResponse = utf8.decode(response.bodyBytes);

      print("decodedResponse");
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
