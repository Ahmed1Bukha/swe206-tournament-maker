// ignore_for_file: avoid_print

import 'dart:convert';
import 'dart:ffi';
import 'dart:io';

import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;
import 'package:http/http.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:swe206/UI_componenets/student_card_admin.dart';
import 'package:swe206/UI_componenets/tournament_card_admin.dart';
import 'package:swe206/UI_componenets/tournament_card_student.dart';
import 'package:swe206/home_admin/student_page_admin.dart';

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

      var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes));
      return decodedResponse;
    } catch (e) {
      throw HttpException("Get request to /$endpoint failed");
    }
  }

  static postRequest(String endpoint, Map body) async {
    try {
      var response = await client.post(
        Uri.http(url, "/$endpoint"),
        body: jsonEncode(body),
        headers: header,
      );

      return response;
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
      print(0);
      print(response);

      if (response.statusCode == 200) {
        print("Pog user");
        var decoderes = jsonDecode(utf8.decode(response.bodyBytes));
        if (decoderes["type"] == "student") {
          //Save ID:
          final SharedPreferences prefs = await SharedPreferences.getInstance();
          await prefs.setString('StudentID', userName);

          Map<String, dynamic> body = {
            "name": decoderes["name"],
            "studentId": int.parse(userName)
          };
          Response res = await postRequest("students", body);

          if (res.bodyBytes.isNotEmpty) {
            var lm = jsonDecode(utf8.decode(res.bodyBytes));
            print(lm);
          }

          return "IsStudent";
        } else if (decoderes["type"] == "admin") {
          return "IsAdmin";
        }
        print(decoderes);
      } else if (response.statusCode == 403) {
        return "Error";
      }
    } catch (e) {
      print(e);
      throw HttpException("failed");
    }
  }

  static dynamic getTournaments() async {
    var tournamentsJson = await getRequest("Tournaments");
    List<dynamic> tournaments = [];
    for (int i = 0; i < tournamentsJson.length; i++) {
      tournaments.add(
        TournamentCardStudent(
          tournamentsJson[i]["name"],
          tournamentsJson[i]["sport"],
          tournamentsJson[i]["type"],
          tournamentsJson[i]["open"].toString(),
          tournamentsJson[i]["tournamentType"],
          tournamentsJson[i]['id'],
          tournamentsJson[i]['startDate'],
          tournamentsJson[i]['endDate'],
          tournamentsJson[i]['studentsPerTeam'],
        ),
      );
    }
    return tournaments;
  }

  static addParticipant(int tournamentID) async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    final String? studentID = prefs.getString('StudentID');

    Map<String, int> body = {
      "tournamentId": tournamentID,
      "participantId": int.parse(studentID.toString())
    };
    Response response = await postRequest("Tournaments/addParticipant", body);

    if (response.statusCode == 200) {
      //Add a snack bar that he registered
      var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;
      return "done";
    } else if (response.statusCode == 404) {
      return "registered";
    }
  }

  static addTeam(String teamName, int tournamentID, List<int> teamIDS) async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    final String? studentID = prefs.getString('StudentID');

    teamIDS.add(int.parse(studentID.toString()));
    Map<String, dynamic> body = {
      "name": teamName,
      "team_members": teamIDS,
      "tournament_id": tournamentID
    };
    Response response = await postRequest("teams", body);

    if (response.statusCode == 200) {
      //Add a snack bar that he registered
      var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;

      return "done";
    } else if (response.statusCode == 404) {
      print(response.body);
      return "registered";
    }
  }

  static dynamic getTournamentsAdmin() async {
    var tournamentsJson = await getRequest("Tournaments");
    List<dynamic> tournaments = [];
    for (int i = 0; i < tournamentsJson.length; i++) {
      tournaments.add(
        TournamentCardAdmin(
          title: tournamentsJson[i]["name"],
          game: tournamentsJson[i]["sport"],
          tournamentBased: tournamentsJson[i]["type"],
          status: tournamentsJson[i]["open"].toString(),
          type: tournamentsJson[i]["tournamentType"],
          id: tournamentsJson[i]['id'],
          startDate: tournamentsJson[i]['startDate'],
          endDate: tournamentsJson[i]['endDate'],
          studentPerTeam: tournamentsJson[i]['studentsPerTeam'],
          matches: tournamentsJson[i]["tournamentMatches"],
          timeBetween: tournamentsJson[i]["timeBetweenStages"].round(),
          isOpen: tournamentsJson[i]["open"],
          isFinished: tournamentsJson[i]["finished"],
        ),
      );
    }
    return tournaments;
  }

  static dynamic getMatchesAdmin() async {
    var tournamentsJson = await getRequest("Tournaments");
  }

  static addElemeniation(
      String name,
      int participantCount,
      String startDate,
      String endDate,
      int timeBetween,
      String tournamentType,
      int numberOfMembers,
      String game) async {
    Map<String, dynamic> body = {
      "name": name,
      "participantCount": participantCount,
      "startDate": startDate,
      "endDate": endDate,
      "timeBetweenStages": timeBetween,
      "tournamentType": tournamentType,
      "sport": game,
      "studentsPerTeam": numberOfMembers
    };
    Response response = await postRequest("EliminationTournaments", body);

    if (response.statusCode == 200) {
      //Add a snack bar that he registered
      var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;

      print(decodedResponse);
      return "done";
    } else if (response.statusCode == 404) {
      return response.body;
    }
  }

  static addRoundRobin(
      String name,
      int participantCount,
      String startDate,
      String endDate,
      int timeBetween,
      String tournamentType,
      int numberOfMembers,
      String game) async {
    Map<String, dynamic> body = {
      "name": name,
      "participantCount": participantCount,
      "startDate": startDate,
      "endDate": endDate,
      "timeBetweenStages": timeBetween,
      "tournamentType": tournamentType,
      "sport": game,
      "studentsPerTeam": numberOfMembers
    };
    Response response = await postRequest("RoundRobinTournaments", body);

    if (response.statusCode == 200) {
      //Add a snack bar that he registered
      var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;

      print(decodedResponse);
      return "done";
    } else if (response.statusCode == 404) {
      return response.body;
    }
  }

  static getGames() async {
    var tournamentsJson = await getRequest("getSports");
    List<String> games = [];
    for (int i = 0; i < tournamentsJson.length; i++) {
      games.add(tournamentsJson[i].toString());
    }
    return games;
  }

  static addGames(String game) async {
    Map<String, dynamic> body = {"name": game};
    var res = await postRequest("addSport", body);
  }

  static dynamic getStudents() async {
    var studentsJson = await getRequest("students");
    print(studentsJson);
    List<dynamic> students = [];
    for (int i = 0; i < studentsJson.length; i++) {
      students.add(StudentCardAdmin(
        studentsJson[i]["studentId"],
        studentsJson[i]["name"],
        studentsJson[i]["teams"],
        studentsJson[i]["tournaments"],
      ));
    }
    return students;
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
