import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/tournament_card_student.dart';
import 'package:swe206/classes/controller_student.dart';

class SearchPageStudent extends StatefulWidget {
  SearchPageStudent(this.tournamentsManager, {super.key});
  ControllerStudent tournamentsManager;
  static String id = "SeachPageStudent";
  @override
  State<SearchPageStudent> createState() => _SearchPageStudentState();
}

class _SearchPageStudentState extends State<SearchPageStudent> {
  String searchTerm = "";
  getSearchResult() async {
    setState(() {
      isLoading = true;
    });
    List<TournamentCardStudent> result =
        await widget.tournamentsManager.searchResult(searchTerm);
    setState(() {
      isLoading = false;
    });
    return result;
  }

  bool isLoading = false;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Search page"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: SingleChildScrollView(
          child: Column(
            children: [
              TextField(
                onChanged: (value) {
                  searchTerm = value;
                },
                decoration: const InputDecoration(
                  label: Text("Search"),
                  hintText: "Enter Tournament name",
                  border: OutlineInputBorder(),
                ),
              ),
              TextButton(
                onPressed: () async {
                  setState(() {});
                },
                child: const Text("Search"),
              ),
              ...isLoading
                  ? getSearchResult()
                  : const CircularProgressIndicator()
            ],
          ),
        ),
      ),
    );
  }
}
