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
  TextEditingController mySearchController = TextEditingController();
  List<dynamic> tournamentsCard = [];

  getSearchResult() async {
    setState(() {
      isLoading = true;
    });

    tournamentsCard =
        await widget.tournamentsManager.searchResult(mySearchController.text);
    setState(() {
      isLoading = false;
    });
  }

  bool isLoading = false;
  @override
  void initState() {
    getSearchResult();
    super.initState();
  }

  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Search page"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          children: [
            TextField(
              controller: mySearchController,
              decoration: const InputDecoration(
                label: Text("Search"),
                hintText: "Enter Tournament name",
                border: OutlineInputBorder(),
              ),
            ),
            TextButton(
              onPressed: () async {
                setState(() {
                  getSearchResult();
                });
              },
              child: const Text("Search"),
            ),
            !isLoading
                ? SingleChildScrollView(
                    child: Column(
                      children: [...tournamentsCard],
                    ),
                  )
                : const CircularProgressIndicator()
          ],
        ),
      ),
    );
  }
}
