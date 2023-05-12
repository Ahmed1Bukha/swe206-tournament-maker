import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/const.dart';

import '../classes/controller_admin.dart';

class SearchPageAdmin extends StatefulWidget {
  const SearchPageAdmin(this.tournamentManager, {super.key});
  final ControllerAdmin tournamentManager;
  @override
  State<SearchPageAdmin> createState() => _SearchPageAdminState();
}

class _SearchPageAdminState extends State<SearchPageAdmin> {
  TextEditingController mySearchController = TextEditingController();

  List<dynamic> tournamentsCard = [];
  getSearchResult() async {
    setState(() {
      isLoading = true;
    });

    tournamentsCard =
        await widget.tournamentManager.searchResult(mySearchController.text);
    setState(() {
      isLoading = false;
    });
  }

  bool isLoading = false;

  @override
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
      body: isLoading
          ? CircularProgressIndicator()
          : Padding(
              padding: const EdgeInsets.all(8.0),
              child: SingleChildScrollView(
                child: Column(
                  children: [
                    TextField(
                      controller: mySearchController,
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
                      onPressed: () {
                        setState(() {
                          getSearchResult();
                        });
                      },
                      child: const Text("Search"),
                    ),
                    ...tournamentsCard
                  ],
                ),
              ),
            ),
    );
  }
}
