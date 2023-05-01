import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';

import '../classes/controller_admin.dart';

class SearchPageAdmin extends StatefulWidget {
  const SearchPageAdmin(this.tournamentManager, {super.key});
  final ControllerAdmin tournamentManager;
  @override
  State<SearchPageAdmin> createState() => _SearchPageAdminState();
}

class _SearchPageAdminState extends State<SearchPageAdmin> {
  String searchTerm = "";
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
                onPressed: () {
                  setState(() {});
                },
                child: const Text("Search"),
              ),
              ...widget.tournamentManager.searchResult(searchTerm),
            ],
          ),
        ),
      ),
    );
  }
}
