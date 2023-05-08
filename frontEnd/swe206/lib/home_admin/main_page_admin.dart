import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/authintication/welcome_page.dart';
import 'package:swe206/classes/controller_admin.dart';
import 'package:swe206/home_admin/create_tournament_page.dart';
import 'package:swe206/home_admin/home_page_admin.dart';
import 'package:swe206/home_admin/modify_pages/modify_page_tournament.dart';
import 'package:swe206/home_admin/search_page_admin.dart';

import '../authintication/auth_page_student.dart';

class MainPageAdmin extends StatefulWidget {
  MainPageAdmin(this._tournamentManagerAdmin, {super.key});
  static String id = "mainAdmin";
  ControllerAdmin _tournamentManagerAdmin;
  @override
  State<MainPageAdmin> createState() => _MainPageAdminState();
  int _selectedIndex = 0;
}

class _MainPageAdminState extends State<MainPageAdmin> {
  @override
  Widget build(BuildContext context) {
    final List<Widget> _widgetOptions = <Widget>[
      HomePageAdmin(widget._tournamentManagerAdmin),
      const CreatePage(),
      SearchPageAdmin(widget._tournamentManagerAdmin),
      const Text("LogOut")
    ];
    return Scaffold(
      bottomNavigationBar: BottomNavigationBar(
        unselectedItemColor: Colors.grey,
        selectedItemColor: Colors.blueAccent,
        currentIndex: widget._selectedIndex,
        onTap: (value) {
          setState(() {
            widget._selectedIndex = value;
          });
          if (widget._selectedIndex == 3) {
            Navigator.pushReplacementNamed(context, WelcomePage.id);
          }
        },
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: "Home",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.plus_one),
            label: "Create",
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
