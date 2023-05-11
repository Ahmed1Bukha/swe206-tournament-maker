import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/const.dart';
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
        backgroundColor: Colors.white,
        elevation: 0,
        title: Text(
          "Search",
          style: h2,
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: SingleChildScrollView(
          child: Column(
            children: [
              TextField(
                controller: mySearchController,
                cursorColor: Colors.black,
                style: const TextStyle(
                  color: Colors.black,
                  fontSize: 20,
                ),
                decoration: InputDecoration(
                  prefixIcon: Icon(Icons.search),
                  hintText: "Entet UserName",
                  filled: true,
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(20),
                    borderSide: BorderSide.none,
                  ),
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
                  ? Column(
                      children: [...tournamentsCard],
                    )
                  : const CircularProgressIndicator()
            ],
          ),
        ),
      ),
    );
  }
}
