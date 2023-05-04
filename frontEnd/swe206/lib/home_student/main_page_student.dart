import 'package:flutter/material.dart';
import 'package:swe206/authintication/auth_page_student.dart';
import 'package:swe206/classes/controller_student.dart';
import 'package:swe206/home_student/home_page_student.dart';
import 'package:swe206/home_student/search_page_student.dart';

import '../authintication/welcome_page.dart';

class MainPageStudent extends StatefulWidget {
  MainPageStudent(this.tournamentsManager, {super.key});
  static String id = "MainPageStudent";
  ControllerStudent tournamentsManager;
  @override
  State<MainPageStudent> createState() => _MainPageStudentState();
  int _selectedIndex = 0;
}

class _MainPageStudentState extends State<MainPageStudent> {
  @override
  Widget build(BuildContext context) {
    final List<Widget> _widgetOptions = <Widget>[
      HomePageStudent(widget.tournamentsManager),
      SearchPageStudent(widget.tournamentsManager),
      const Text("LogOut")
    ];
    return Scaffold(
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: widget._selectedIndex,
        onTap: (value) {
          setState(() {
            widget._selectedIndex = value;
          });
          if (widget._selectedIndex == 2) {
            Navigator.pushReplacementNamed(context, WelcomePage.id);
          }
        },
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: "Home",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.search),
            label: "Search",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.door_back_door_outlined),
            label: "Logout",
          ),
        ],
      ),
      body: _widgetOptions.elementAt(widget._selectedIndex),
    );
  }
}
