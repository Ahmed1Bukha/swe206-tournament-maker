import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

class Requests {
  static var client = http.Client();
  String url = '10.0.2.2:8080';
  Map<String, String> header = {"Content-Type": "application/json"};

  Future<Map> getRequest(String endpoint) async {
    try {
      var response = await client.get(
        Uri.http(url, "/$endpoint"),
        headers: header,
      );
      var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;
      return decodedResponse;
    } catch (e) {
      throw HttpException("Get request to /$endpoint failed");
    }
  }

  Future<Map> postRequest(String endpoint, Map body) async {
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
